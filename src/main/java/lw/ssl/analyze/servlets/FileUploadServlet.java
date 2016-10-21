package lw.ssl.analyze.servlets;

import api.lw.ssl.analyze.enums.HostAssessmentStatus;
import lw.ssl.analyze.pojo.TotalScanResults;
import lw.ssl.analyze.pojo.UserData;
import lw.ssl.analyze.pojo.WebResourceDescription;
import lw.ssl.analyze.pojo.securityheaders.SecurityHeadersResults;
import lw.ssl.analyze.pojo.ssllabs.SslLabsResults;
import lw.ssl.analyze.pojo.virustotal.VirusTotalResults;
import lw.ssl.analyze.report.ExcelReportBuilder;
import lw.ssl.analyze.utils.InputStreamConverter;
import lw.ssl.analyze.utils.external.SecurityHeadersUtil;
import lw.ssl.analyze.utils.external.SslLabsUtil;
import lw.ssl.analyze.utils.external.VirusTotalUtil;
import lw.ssl.analyze.utils.notificators.EmailNotifier;
import lw.ssl.analyze.utils.notificators.FileExtension;
import lw.ssl.analyze.utils.validate.PartValidate;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by zmushko_m on 21.04.2016.
 */

@WebServlet(name = "FileUploadServlet", urlPatterns = {"/uploadCSV"})
@MultipartConfig()
public class FileUploadServlet extends HttpServlet {
    private static final String FILE_PART_NAME = "fileCSV";

    public static final String WRONG_URLs_LETTER_SUBJECT = "SSL Scan Results";

    private Map<UserData, WeakReference<Task>> results = new HashMap<>();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = (String) request.getSession(false).getAttribute("user");
        String eMail = (String) request.getSession(false).getAttribute("eMail");

        UserData userData = new UserData(userName, eMail);

        Task currentTask = results.get(userName) == null ? null : results.get(userName).get();

        Part filePart = request.getPart(FILE_PART_NAME);

        String validateError = PartValidate.isFileValid(filePart);
        if (StringUtils.isNotBlank(validateError)) {
            response.setContentType("text/html");
            String redirectPage = "/index.jsp" +
                    "?errorMessage=" +
                    URLEncoder.encode(validateError, "UTF-8");
            response.sendRedirect(redirectPage);
            return;
        }

        if (currentTask != null && currentTask.percent < 100) {
            currentTask.interrupt();
        }

        InputStream fileInputStream = filePart.getInputStream();
        currentTask = new Task(InputStreamConverter.convertToWebResourceDescriptions(fileInputStream), eMail);
        currentTask.start();

        results.put(userData, new WeakReference<>(currentTask));

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

    private class Task extends Thread {
        int analyzedCount;
        private String eMailTo;
        private List<TotalScanResults> analyzedHosts;
        private List<WebResourceDescription> webResourceDescriptionList;
        private HashMap<VerificationThread, String> currentUrl;
        private int percent;

        Task(List<WebResourceDescription> webResourceDescriptionlist, String eMail) {
            this.webResourceDescriptionList = webResourceDescriptionlist;
            this.eMailTo = eMail;
        }

        @Override
        public void run() {
            percent = 0;
            analyzedCount = 0;

            analyzedHosts = new ArrayList<>();
            currentUrl = new HashMap<>();


            SslLabsUtil.SSLInfo sslInfo = new SslLabsUtil.SSLInfo(0, 0, 1000);
            while (sslInfo.getPossibleAssessmentsAmount() == 0) {
                sslInfo = SslLabsUtil.getCountOfPossibleAssessments();
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
                try {
                    ByteArrayOutputStream emailAttachments = new ByteArrayOutputStream();
                    ExcelReportBuilder.buildReport(analyzedHosts).write(emailAttachments);
                    EmailNotifier.notifyWithAttachment(emailAttachments, FileExtension.XLS, WRONG_URLs_LETTER_SUBJECT, eMailTo);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            percent = 100;
        }

        int getPercent() {
            return percent;
        }

        synchronized void incrementAnalyzedCount() {
            percent = Math.round(++analyzedCount * 100 / webResourceDescriptionList.size());
        }

        synchronized String getCurrentUrl() {
            StringBuilder result = new StringBuilder();
            for (String url : currentUrl.values()) {
                result.append(url).append(", ");
            }
            return result.toString();
        }

        class VerificationThread implements Runnable {
            WebResourceDescription webResourceDescription;

            VerificationThread(WebResourceDescription webResourceDescription) {
                this.webResourceDescription = webResourceDescription;
            }

            @Override
            public void run() {

                String urlToCheck = webResourceDescription.getHost() + (webResourceDescription.getPort() == null ?
                        "" : ":" + webResourceDescription.getPort());

                //NEW CODE
                VirusTotalResults virusTotalResults = VirusTotalUtil.getStatistics(urlToCheck);
                SecurityHeadersResults securityHeadersResults = SecurityHeadersUtil.getStatistics(urlToCheck, 0);
                //-- NEW CODE
                System.out.println("Analize url:" + webResourceDescription.getHost());
                currentUrl.put(this, urlToCheck);

                SslLabsResults sslLabsResults = null;

                SslLabsUtil.SSLInfo sslInfo = new SslLabsUtil.SSLInfo(0, 0, 1000);
                boolean wasAssessmentDone = false;
                int amountOfAttempts = 0;

                while (!wasAssessmentDone && !(amountOfAttempts > 80)) {
                    while (sslInfo.getPossibleAssessmentsAmount() == 0) {
                        sslInfo = SslLabsUtil.getCountOfPossibleAssessments();
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
                        sslLabsResults = SslLabsUtil.getStatistics(webResourceDescription.getHost(), webResourceDescription.getPort(), true, null);
                        wasAssessmentDone = true;
                    } catch (IOException ex) {
                        if (ex.getMessage().startsWith("Server returned HTTP response code: 429")) {
                            System.out.println("Error 429:" + webResourceDescription.getHost());
                            sslInfo = new SslLabsUtil.SSLInfo(0, 0, 1000);
                        } else {
                            ex.printStackTrace();
                            break;
                        }
                    }
                }

                if (sslLabsResults == null) {
                    sslLabsResults = SslLabsResults.getErrorResults(webResourceDescription.getHost(),
                            webResourceDescription.getPort(), HostAssessmentStatus.ERROR.getName(),
                            "Information had not been received from server");
                }

                incrementAnalyzedCount();
                TotalScanResults totalScanResults = TotalScanResults.getTotalResults
                        (securityHeadersResults, virusTotalResults, sslLabsResults, null);
                analyzedHosts.add(totalScanResults);

                currentUrl.remove(this);
                System.out.println("Url:" + webResourceDescription.getHost() + " done");
            }
        }
    }
}
