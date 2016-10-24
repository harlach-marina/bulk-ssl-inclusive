package lw.ssl.analyze.utils.external;

import lw.ssl.analyze.pojo.virustotal.VirusTotalResults;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created on 05.10.2016;
 *
 * @author p.sinitskiy (adronex303@gmail.com);
 * @since 1.0.
 */
public final class VirusTotalUtil {

    private static final String API_KEY = "f982a78d18ca41d511662e478ecf2d9f6c7659377e10b796f481afb85de1f7fd";
    private static final String API_URL = "https://www.virustotal.com/vtapi/v2/url/";
    private static final Long REQUEST_REPEATING_INTERVAL = 10000L;
    private static final String SCAN_REPORT_SUCCESSFUL_MESSAGE = "Scan finished, scan information embedded in this object";
    private static final Integer MAX_ITERATIONS_COUNT = 10;

    private VirusTotalUtil() {
    }

    public static VirusTotalResults getStatistics(String urlToCheck, Integer iteration) {
        try {
            System.out.println("Virus total scanning started!");
            sendRequestForScan(urlToCheck);
            Thread.sleep(REQUEST_REPEATING_INTERVAL);
            JSONObject scanResults = sendRequestForResults(urlToCheck);
            if (!SCAN_REPORT_SUCCESSFUL_MESSAGE.equals(scanResults.optString("verbose_msg"))) {
                System.out.println("Virus total error!");
                return getStatistics(urlToCheck, ++iteration);
            } else {
                System.out.println("Virus total scanning finished!");
                return new VirusTotalResults(scanResults.optJSONObject("scans"));
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static JSONObject sendRequestForScan(String urlToCheck) throws IOException {
        String urlToSend = URLEncoder.encode(urlToCheck, "UTF-8");
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(API_URL + "scan?apikey=" + API_KEY +
                "&url=" + urlToSend);
        HttpResponse response = httpclient.execute(httppost);
        String accessTokenString = EntityUtils.toString(response.getEntity());
        return new JSONObject(accessTokenString);
    }

    private static JSONObject sendRequestForResults(String urlToCheck) throws IOException {
        String urlToSend = URLEncoder.encode(urlToCheck, "UTF-8");
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(API_URL + "report?apikey=" + API_KEY +
                "&resource=" + urlToSend);
        HttpResponse response = httpclient.execute(httppost);
        String accessTokenString = EntityUtils.toString(response.getEntity());
        return new JSONObject(accessTokenString);
    }
}
