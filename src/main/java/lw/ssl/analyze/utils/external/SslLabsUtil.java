package lw.ssl.analyze.utils.external;

import api.lw.ssl.analyze.enums.HostAssessmentStatus;
import lw.ssl.analyze.pojo.ssllabs.SslLabsResults;
import lw.ssl.analyze.utils.ResourceContainer;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by zmushko_m on 25.04.2016.
 */
public final class SslLabsUtil {
    private static final int CONNECT_TIMEOUT_MS = 30 * 1000;
    private static final int CONTENT_READ_TIMEOUT_MS = 30 * 1000;

    private SslLabsUtil() {
    }

    public static class SSLInfo {
        private int maxAssessments;
        private int currentAssessments;
        private int newAssessmentCoolOff;

        SSLInfo() {
        }

        public SSLInfo(int maxAssessments, int currentAssessments, int newAssessmentCoolOff) {
            this.maxAssessments = maxAssessments;
            this.currentAssessments = currentAssessments;
            this.newAssessmentCoolOff = newAssessmentCoolOff;
        }

        public int getPossibleAssessmentsAmount() {
            return maxAssessments - currentAssessments;
        }

        public int getMaxAssessments() {
            return maxAssessments;
        }

        public int getCurrentAssessments() {
            return currentAssessments;
        }

        public int getNewAssessmentCoolOff() {
            return newAssessmentCoolOff;
        }
    }

    public static SSLInfo getCountOfPossibleAssessments() {
        InputStream inputInfoStream;

        try {
            final URL getStatisticUrl = new URL(ResourceContainer.getSSLLabsInfoUrl());
            final URLConnection connection = getStatisticUrl.openConnection();
            connection.setConnectTimeout(CONNECT_TIMEOUT_MS);
            connection.setReadTimeout(CONTENT_READ_TIMEOUT_MS);
            inputInfoStream = connection.getInputStream();

            String analysisResponseString = IOUtils.toString(inputInfoStream);
            JSONObject infoJSON = new JSONObject(analysisResponseString);

            return new SSLInfo(infoJSON.getInt("maxAssessments"), infoJSON.getInt("currentAssessments"), infoJSON.getInt("newAssessmentCoolOff"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new SSLInfo();
    }
    public static SslLabsResults getStatistics(final String host) throws IOException {
        return getStatistics(host, null, true, null);
    }

    public static SslLabsResults getStatistics(final String host, final String port, boolean isNewAssessment, Integer attemptCount) throws IOException {
        if (attemptCount == null) {
            System.out.println("SSL Labs scanning started!");
            attemptCount = 0;
        } else {
            if (++attemptCount > 60) {
                return null;
            }
        }

        InputStream inputAnalysisStream = null;

        try {
            final URL getStatisticUrl = new URL(ResourceContainer.getSSLLabsAnalysisUrl(host, port, isNewAssessment));
            final URLConnection connection = getStatisticUrl.openConnection();
            connection.setConnectTimeout(CONNECT_TIMEOUT_MS);
            connection.setReadTimeout(CONTENT_READ_TIMEOUT_MS);
            inputAnalysisStream = connection.getInputStream();

            String analysisResponseString = IOUtils.toString(inputAnalysisStream);
            JSONObject analysisResponseJSON = new JSONObject(analysisResponseString);

            String currentHostAssessmentStatus = analysisResponseJSON.getString("status");

            if (HostAssessmentStatus.READY.getName().equals(currentHostAssessmentStatus) ||
                    HostAssessmentStatus.ERROR.getName().equals(currentHostAssessmentStatus)) {
                System.out.println("SSL Labs scanning finished!");
                return SslLabsResults.createFromResponse(analysisResponseJSON);
            } else {
                int timerInterval;

                if (!HostAssessmentStatus.IN_PROGRESS.getName().equals(currentHostAssessmentStatus)) {
                    timerInterval = 5000;
                } else {
                    timerInterval = 10000;
                }

                try {
                    Thread.sleep(timerInterval);
                    return getStatistics(host, port, false, attemptCount);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } finally {
            if (inputAnalysisStream != null) {
                try {
                    inputAnalysisStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }
}
