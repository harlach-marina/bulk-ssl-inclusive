package lw.ssl.analyze.utils.external;

import lw.ssl.analyze.pojo.securityheaders.SecurityHeadersResults;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

/**
 * Created on 05.10.2016;
 *
 * @author p.sinitskiy (adronex303@gmail.com);
 * @since 1.0.
 */
public final class SecurityHeadersUtil {

    private static final String JVM_CERTIFICATES_PASS = "changeit";
    private static final String SITE_URL = "https://securityheaders.io/";
    private static final String CERTIFICATE_NAME = "schd.io";
    private static final String CERTIFICATE_PATH = "/schd.io.crt"; // real path: src/main/resources/schd.io.crt
    private static final Long REQUEST_REPEATING_INTERVAL = 1000L;
    private static final Integer REQUESTS_COUNT = 10;

    private SecurityHeadersUtil() {
    }

    public static SecurityHeadersResults getStatistics(String urlToCheck, Integer count) {
        System.out.println("Security headers scanning started!");
        importCertificate();
        Document doc = Jsoup.parse(sendRequestForResult(urlToCheck));
        Elements headersTable = doc.select(".pillList");
        Elements greenHeaders = headersTable.select(".headerItem.pill.pill-green");
        Elements redHeaders = headersTable.select(".headerItem.pill.pill-red");
        if (count > REQUESTS_COUNT) {
            return new SecurityHeadersResults();
        }
        if (redHeaders.isEmpty() && greenHeaders.isEmpty()) {
            System.out.println("Security headers check failed!");
            try {
                Thread.sleep(REQUEST_REPEATING_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return getStatistics(urlToCheck, ++count);
        }
        SecurityHeadersResults results = new SecurityHeadersResults();
        for (Element element : redHeaders) {
            for (Node node : element.childNodes()) {
                if (node instanceof TextNode) {
                    results.getRedHeaders().add(((TextNode) node).text());
                }
            }
        }
        for (Element element : greenHeaders) {
            for (Node node : element.childNodes()) {
                if (node instanceof TextNode) {
                    results.getGreenHeaders().add(((TextNode) node).text());
                }
            }
        }
        System.out.println("Security headers scanning finished!");
        return results;
    }

    private static void importCertificate() {
        InputStream certIn = SecurityHeadersUtil.class.getResourceAsStream(CERTIFICATE_PATH);

        final char sep = File.separatorChar;
        File dir = new File(System.getProperty("java.home") + sep + "lib" + sep + "security");
        File file = new File(dir, "cacerts");
        try {
            InputStream localCertIn = new FileInputStream(file);

            char[] passphrase = JVM_CERTIFICATES_PASS.toCharArray();
            KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
            keystore.load(localCertIn, passphrase);
            if (keystore.containsAlias(CERTIFICATE_NAME)) {
                certIn.close();
                localCertIn.close();
                return;
            }
            localCertIn.close();

            BufferedInputStream bis = new BufferedInputStream(certIn);
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            while (bis.available() > 0) {
                Certificate cert = cf.generateCertificate(bis);
                keystore.setCertificateEntry(CERTIFICATE_NAME, cert);
            }

            certIn.close();

            OutputStream out = new FileOutputStream(file);
            keystore.store(out, passphrase);
            out.close();
        } catch (IOException | CertificateException | NoSuchAlgorithmException | KeyStoreException e) {
            e.printStackTrace();
        }
    }

    private static String sendRequestForResult(String urlToCheck) {
        try {
            String urlToSend = URLEncoder.encode(urlToCheck, "UTF-8");
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(SITE_URL + "?q=" + urlToSend + "&followRedirects=on");
            HttpResponse response = httpclient.execute(httpGet);
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
