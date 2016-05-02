package lw.ssl.analyze.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;

/**
 * Created by zmushko_m on 21.04.2016.
 */
public class ResourceContainer {
    private static final String SSL_LABS_URL = "https://api.ssllabs.com/api/v2/";
    private static final String SSL_LABS_ANALYSIS_PREFIX = "analyze?all=on&ignoreMismatch=on";
    private static final String SSL_LABS_INFO_PREFIX = "info";

    public static String getSSLLabsAnalysisUrl(String host, String port, boolean isNewAssessment) {
        StringBuffer sslLabsUrl = new StringBuffer().append(SSL_LABS_URL).append(SSL_LABS_ANALYSIS_PREFIX);

        if (StringUtils.isNotBlank(host)) {
            sslLabsUrl.append(MessageFormat.format("&host={0}", host));
        }
        if (StringUtils.isNotBlank(port)) {
            sslLabsUrl.append(MessageFormat.format("&port={0}", port));
        }
        if (isNewAssessment) {
            sslLabsUrl.append("&startNew=on");
        }

        return sslLabsUrl.toString();
    }

    public static String getSSLLabsInfoUrl() {
        return SSL_LABS_URL + SSL_LABS_INFO_PREFIX;
    }
}
