package lw.ssl.analyze.restapi;


import lw.ssl.analyze.pojo.dnssec.DnsSecAnalyzerResults;
import lw.ssl.analyze.pojo.securityheaders.SecurityHeadersResults;
import lw.ssl.analyze.pojo.virustotal.VirusTotalResults;
import lw.ssl.analyze.report.PdfReport;
import lw.ssl.analyze.utils.external.DnsSecAnalyzerUtil;
import lw.ssl.analyze.utils.external.SecurityHeadersUtil;
import lw.ssl.analyze.utils.external.VirusTotalUtil;
import lw.ssl.analyze.utils.notificators.EmailNotifier;
import lw.ssl.analyze.utils.notificators.FileExtension;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

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
                              @QueryParam("url") String url){
        try {
       //     SslLabsResults sslLabsResults = SslLabsUtil.getStatistics(url);
            VirusTotalResults virusTotalResults = VirusTotalUtil.getStatistics(url);
            SecurityHeadersResults securityHeadersResults = SecurityHeadersUtil.getStatistics(url);
      //      TlsCheckResults tlsCheckResults= TlsCheckUtil.getStatistics(email);
            DnsSecAnalyzerResults dnsSecAnalyzerResults = DnsSecAnalyzerUtil.getStatistics(url);

            PdfReport pdfReport = new PdfReport.PdfReportBuilder(url)
                    .virusTotalResults(virusTotalResults)
                    .securityHeadersResults(securityHeadersResults)
       //             .sslLabsResults(sslLabsResults)
        //            .tlsCheckResults(tlsCheckResults)
                    .dnsSecAnalyzerResults(dnsSecAnalyzerResults)
                    .build();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            pdfReport.getDocument().save(byteArrayOutputStream);
            EmailNotifier.notifyWithAttachment(byteArrayOutputStream, FileExtension.PDF, REPORT_SUBJECT, email);
            return Response.status(200).entity(SUCCESS_MESSAGE).build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Response.status(400).build();
    }
}
