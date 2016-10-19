package lw.ssl.analyze.utils.external;

import lw.ssl.analyze.pojo.dnssec.DnsSecAnalyzerResults;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created on 19.10.2016;
 *
 * @author p.sinitskiy (adronex303@gmail.com);
 * @since 1.0.
 */
public final class DnsSecAnalyzerUtil {

    private static final String SITE_URL = "http://dnssec-debugger.verisignlabs.com/";

    private DnsSecAnalyzerUtil() {
    }

    public static DnsSecAnalyzerResults getStatistics(String urlToCheck) {
        String urlToSend = urlToCheck.replaceAll("https://", "").replaceAll("http://", "");
        Document doc = Jsoup.parse(sendRequestForResult(urlToSend));
        Elements elements = doc.select(".T1 > tbody > tr:last-child").select("tr.L0");
        Set<String> redResults = new TreeSet<>();
        Set<String> greenResults = new TreeSet<>();
        for (Element element : elements) {
            String resultText = element.select("td").get(1).text();
            if (!"".equals(element.id())) {
                redResults.add(resultText);
            } else {
                greenResults.add(resultText);
            }
        }
        return new DnsSecAnalyzerResults(redResults, greenResults);
    }

    private static String sendRequestForResult(String urlToCheck) {
        try {
            String urlToSend = URLEncoder.encode(urlToCheck, "UTF-8");
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(SITE_URL + urlToSend);
            HttpResponse response = httpclient.execute(httpGet);
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
