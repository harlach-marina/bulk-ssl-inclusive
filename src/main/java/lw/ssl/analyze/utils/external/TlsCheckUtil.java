package lw.ssl.analyze.utils.external;

import lw.ssl.analyze.pojo.tlscheck.TlsCheckResults;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created on 18.10.2016;
 *
 * @author p.sinitskiy (adronex303@gmail.com);
 * @since 1.0.
 */
public final class TlsCheckUtil {

    private static final String SITE_URL = "http://checktls.com/perl/TestReceiver.pl/";

    private TlsCheckUtil() {
    }

    public static TlsCheckResults getStatistics(String emailToCheck) {
        System.out.println("TLS Check scanning started!");
        Document doc = Jsoup.parse(sendRequestForResult(emailToCheck));
        Elements resultsTable = doc.select("#Matrix");
        Elements percentsResults = resultsTable.select(".totalrow");
        Elements tlsAdv = percentsResults.select("td:nth-child(6)");
        Elements certOk = percentsResults.select("td:nth-child(7)");
        Elements tlsNeg = percentsResults.select("td:nth-child(8)");
        Integer tlsAdvPercents = null;
        Integer certOkPercents = null;
        Integer tlsNegPercents = null;
        for (Element element : tlsAdv) {
            for (Node node : element.childNodes()) {
                String percentsAsText = ((TextNode)node).text().replaceAll("%", "");
                tlsAdvPercents = Integer.parseInt(percentsAsText);
            }
        }
        for (Element element : certOk) {
            for (Node node : element.childNodes()) {
                String percentsAsText = ((TextNode)node).text().replaceAll("%", "");
                certOkPercents = Integer.parseInt(percentsAsText);
            }
        }
        for (Element element : tlsNeg) {
            for (Node node : element.childNodes()) {
                String percentsAsText = ((TextNode)node).text().replaceAll("%", "");
                tlsNegPercents = Integer.parseInt(percentsAsText);
            }
        }
        System.out.println("TLS Check scanning finished!");
        return new TlsCheckResults(tlsAdvPercents, certOkPercents, tlsNegPercents);
    }

    private static String sendRequestForResult(String emailToCheck) {
        try {
            String emailToSend = URLEncoder.encode(emailToCheck, "UTF-8");
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(SITE_URL);
            String body = "KEYWORDS=&EMAIL=" + emailToSend +
                    "&LEVEL=2&HOST=&PORT=&RELAXWC=&AUTH=&AUTHUSER=&AUTHPASS=&TIMEOUT=0";
            HttpEntity httpEntity = new ByteArrayEntity(body.getBytes());
            httpPost.setEntity(httpEntity);
            HttpResponse response = httpclient.execute(httpPost);
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
