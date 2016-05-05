package lw.ssl.analyze.servlets;

import api.lw.ssl.analyze.enums.HostAssessmentStatus;
import lw.ssl.analyze.pojo.UserData;
import lw.ssl.analyze.pojo.WebResourceDescription;
import lw.ssl.analyze.report.ExcelReportBuilder;
import lw.ssl.analyze.utils.InputStreamConverter;
import lw.ssl.analyze.utils.SSLTest;
import lw.ssl.analyze.utils.notificators.EmailNotificator;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by zmushko_m on 21.04.2016.
 */

@WebServlet(name = "FileUploadServlet", urlPatterns = {"/uploadCSV"})
@MultipartConfig
public class FileUploadServlet extends HttpServlet {
    public static final String FILE_PART_NAME = "fileCSV";

    private static final String WRONG_URLs_LETTER_SUBJECT = "Wrong URLs list";
    Map<UserData, WeakReference<Task>> results = new HashMap<>();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = (String) request.getSession(false).getAttribute("user");
        String eMail = (String) request.getSession(false).getAttribute("eMail");

        UserData userData = new UserData(userName, eMail);

        Task currentTask = results.get(userName) == null ? null : results.get(userName).get();

        if(currentTask !=null && currentTask.percent < 100){
            currentTask.interrupt();
        }

        Part filePart = request.getPart(FILE_PART_NAME);
        InputStream fileInputStream = filePart.getInputStream();
        currentTask = new Task(InputStreamConverter.convertToWebResourceDescriptions(fileInputStream), eMail);
        currentTask.start();

        results.put(userData, new WeakReference<Task>(currentTask));

        request.setAttribute("wrongWebResourceStatusList", new ArrayList<>());
        request.setAttribute("percent", 0);
        request.setAttribute("currentUrl", "");

        request.getRequestDispatcher("/result.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = (String) request.getSession(false).getAttribute("user");
        String eMail = (String) request.getSession(false).getAttribute("eMail");

        UserData userData = new UserData(userName, eMail);

        Task currentTask = results.get(userData) == null ? null : results.get(userData).get();
        if (currentTask == null) {
            request.setAttribute("percent", 0);
            request.setAttribute("currentUrl", "");
        } else {
            request.setAttribute("percent", currentTask.getPercent());
            request.setAttribute("currentUrl", currentTask.getCurrentUrl());
        }

        request.getRequestDispatcher("/result.jsp").forward(request, response);
    }

    class Task extends Thread {
        private String eMailTo;
        private List<JSONObject> analyzedHosts;
        private List<WebResourceDescription> webResourceDescriptionList;
        private HashMap<VerificationThread, String> currentUrl;
        private int percent;

        public Task(List<WebResourceDescription> webResourceDescriptionlist, String eMail) {
            this.webResourceDescriptionList = webResourceDescriptionlist;
            this.eMailTo = eMail;
        }

        int analyzedCount;

        @Override
        public void run() {
            percent = 0;
            analyzedCount = 0;

            analyzedHosts = new ArrayList<>();
            currentUrl = new HashMap<>();


            SSLTest.SSLInfo sslInfo = new SSLTest.SSLInfo(0, 0, 1000);
            while (sslInfo.getPossibleAssessmentsAmount() == 0) {
                sslInfo = SSLTest.getCountOfPossibleAssessments();
                if (sslInfo.getPossibleAssessmentsAmount() == 0) {
                    try {
                        Thread.sleep(sslInfo.getNewAssessmentCoolOff());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            ExecutorService executor = Executors.newFixedThreadPool(10);
            //for each url with port (default 443)
            for (WebResourceDescription webResourceDescription : webResourceDescriptionList) {
                VerificationThread verificationThread = new VerificationThread(webResourceDescription);
                executor.submit(verificationThread);

                try {
                    Thread.sleep(sslInfo.getNewAssessmentCoolOff());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            executor.shutdown();

            try {
                executor.awaitTermination(1, TimeUnit.DAYS);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }

            if (analyzedHosts.size() > 0) {
                //Create ExcelReport and Email-notification,
                EmailNotificator.notificate(ExcelReportBuilder.buildReport(analyzedHosts, getServletContext()), WRONG_URLs_LETTER_SUBJECT, getServletContext(), eMailTo);
            }

            percent = 100;
        }

        class VerificationThread implements Runnable {
            WebResourceDescription webResourceDescription;

            VerificationThread(WebResourceDescription webResourceDescription) {
                this.webResourceDescription = webResourceDescription;
            }

            @Override
            public void run() {
                System.out.println("Analize url:" + webResourceDescription.getHost());
                currentUrl.put(this,webResourceDescription.getHost() + (webResourceDescription.getPort() == null ? "" : ":" + webResourceDescription.getPort()));

                JSONObject analysisResponseJSON = null;

                SSLTest.SSLInfo sslInfo = new SSLTest.SSLInfo(0, 0, 1000);
                boolean wasAssessmentDone = false;
                int amountOfAttempts = 0;

                while (!wasAssessmentDone && !(amountOfAttempts > 80)) {
                    while (sslInfo.getPossibleAssessmentsAmount() == 0) {
                        sslInfo = SSLTest.getCountOfPossibleAssessments();
                        if (sslInfo.getPossibleAssessmentsAmount() == 0) {
                            try {
                                Thread.sleep(sslInfo.getNewAssessmentCoolOff());
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    try {
                        amountOfAttempts++;
                        analysisResponseJSON = SSLTest.getStatistic(webResourceDescription.getHost(), webResourceDescription.getPort(), true, null);
                        wasAssessmentDone = true;
                    } catch (IOException ex) {
                        if (ex.getMessage().startsWith("Server returned HTTP response code: 429")) {
                            System.out.println("Error 429:" + webResourceDescription.getHost());
                            sslInfo = new SSLTest.SSLInfo(0, 0, 1000);
                            continue;
                        } else {
                            ex.printStackTrace();
                            break;
                        }
                    }
                }

                if (analysisResponseJSON == null) {
                    analysisResponseJSON = new JSONObject();
                    analysisResponseJSON.put("host", webResourceDescription.getHost());
                    analysisResponseJSON.put("port", webResourceDescription.getPort());
                    analysisResponseJSON.put("status", HostAssessmentStatus.ERROR.getName());
                    analysisResponseJSON.put("statusMessage", "Information had not been received from server");
                }

                incrementAnalyzedCount();
                analyzedHosts.add(analysisResponseJSON);

                currentUrl.remove(this);
                System.out.println("Url:" + webResourceDescription.getHost() + " done");
            }
        }

        public int getPercent() {
            return percent;
        }

        public synchronized void incrementAnalyzedCount() {
            percent = Math.round( ++analyzedCount * 100 / webResourceDescriptionList.size());
        }

        synchronized public String getCurrentUrl() {
            StringBuilder result = new StringBuilder();
            for (String url : currentUrl.values()){
                result.append(url).append(", ");
            }
            return result.toString();
        }
    }
}
