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
    private static final String SUCCESS_MESSAGE = "Report will be sent to your email";

    @GET
    public Response getReport(@QueryParam("email") String email,
                              @QueryParam("username") String username,
                              @QueryParam("url") String url){
        startThreads(email, url);
        return Response.status(200).entity(SUCCESS_MESSAGE).build();
    }

    private void startThreads(String email, String url) {
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
                    totalScanResults.setTlsCheckResults(TlsCheckUtil.getStatistics(email));
                });
                tlsCheckThread.start();
                VirusTotalResults virusTotalResults = VirusTotalUtil.getStatistics(url);
                SecurityHeadersResults securityHeadersResults = SecurityHeadersUtil.getStatistics(url);
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
                EmailNotifier.notifyWithAttachment(byteArrayOutputStream, FileExtension.PDF, REPORT_SUBJECT, email);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Analysis finished at " + new Date() + "!");
        });
        maimThread.start();
    }
}
