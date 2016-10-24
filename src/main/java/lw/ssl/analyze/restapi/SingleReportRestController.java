package lw.ssl.analyze.restapi;


import lw.ssl.analyze.pojo.TotalScanResults;
import lw.ssl.analyze.pojo.dnssec.DnsSecAnalyzerResults;
import lw.ssl.analyze.pojo.securityheaders.SecurityHeadersResults;
import lw.ssl.analyze.pojo.virustotal.VirusTotalResults;
import lw.ssl.analyze.report.PdfReport;
import lw.ssl.analyze.utils.external.*;
import lw.ssl.analyze.utils.notificators.EmailNotifier;
import lw.ssl.analyze.utils.notificators.FileExtension;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

/**
 * Created on 14.10.2016;
 *
 * @author p.sinitskiy (adronex303@gmail.com);
 * @since 1.0.
 */
@Path("/report")
public class SingleReportRestController {

    private static final String REPORT_SUBJECT = "Analysis results.";

    @GET
    public Response getReport(@QueryParam("emailForReport") String emailForReport,
                              @QueryParam("emailForCheck") String emailForCheck,
                              @QueryParam("url") String url) {
        try {
            if (emailForReport != null && url != null) {
                if (emailForCheck == null) {
                    startThreads(URLDecoder.decode(emailForReport, "UTF-8"),
                            null,
                            URLDecoder.decode(url, "UTF-8"));
                } else {
                    startThreads(URLDecoder.decode(emailForReport, "UTF-8"),
                            URLDecoder.decode(emailForCheck, "UTF-8"),
                            URLDecoder.decode(url, "UTF-8"));
                }
                return Response.status(200).build();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return Response.status(400).build();
    }

    private void startThreads(String emailForReport, String emailForCheck, String url) {
        Thread maimThread = new Thread(() -> {
            System.out.println("Analysis started at " + new Date() + "!");
            try {
                TotalScanResults totalScanResults = new TotalScanResults();
                Thread sslLabsThread = new Thread(() -> {
                    try {
                        totalScanResults.setSslLabsResults(SslLabsUtil.getStatistics(url));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                sslLabsThread.start();
                Thread tlsCheckThread = new Thread(() -> {
                    totalScanResults.setTlsCheckResults(TlsCheckUtil.getStatistics(emailForCheck));
                });
                if (emailForCheck != null) {
                    tlsCheckThread.start();
                }
                VirusTotalResults virusTotalResults = VirusTotalUtil.getStatistics(url, 0);
                SecurityHeadersResults securityHeadersResults = SecurityHeadersUtil.getStatistics(url, 0);
                DnsSecAnalyzerResults dnsSecAnalyzerResults = DnsSecAnalyzerUtil.getStatistics(url);

                sslLabsThread.join();
                tlsCheckThread.join();
                PdfReport pdfReport = new PdfReport.PdfReportBuilder(url)
                        .virusTotalResults(virusTotalResults)
                        .securityHeadersResults(securityHeadersResults)
                        .sslLabsResults(totalScanResults.getSslLabsResults())
                        .tlsCheckResults(totalScanResults.getTlsCheckResults())
                        .dnsSecAnalyzerResults(dnsSecAnalyzerResults)
                        .build();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                pdfReport.getDocument().save(byteArrayOutputStream);
                EmailNotifier.notifyWithAttachment(byteArrayOutputStream, FileExtension.PDF, REPORT_SUBJECT, emailForReport);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                EmailNotifier.notify("Analysis report error", e.getMessage(), emailForReport);
            }
            System.out.println("Analysis finished at " + new Date() + "!");
        });
        maimThread.start();
    }
}
