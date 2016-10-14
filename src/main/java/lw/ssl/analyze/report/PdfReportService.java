package lw.ssl.analyze.report;

import lw.ssl.analyze.pojo.TotalResults;
import lw.ssl.analyze.pojo.securityheaders.SecurityHeadersResults;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 10.10.2016;
 *
 * @author p.sinitskiy (adronex303@gmail.com);
 * @since 1.0.
 */
public class PdfReportService {

    private static final String PDF_REPORT_FILE_PATH = "D:/report.pdf";
    private static final Integer PARAGRAPH_FONT_SIZE = 12;
    private static final Integer TABLE_TITLE_FONT_SIZE = 8;
    private static final Integer LINE_WIDTH = 250;

    private static final Float COMMON_Y_OFFSET_BETWEEN_BLOCKS = 20F;
    private static final Float REPUTATION_BLOCK_Y_OFFSET = 250F;

    public static void buildReport(List<TotalResults> analyzedHosts) {

        List<String> headers = new ArrayList<>();
        headers.add("Ololo");
        headers.add("X-Frame-Options");
        String url = "Url-ololo-yayayayaya";
        PDDocument doc = new PdfReport.PdfReportBuilder(url)
                .reputation(60)
                .compliance(true)
                .securityWeb(headers, SecurityHeadersResults.MAX_RED_HEADERS)
                .confidentiality(true, 34, 56, 70)
                .integrity(false)
                .build()
                .getDocument();

//        for (TotalResults totalResults : analyzedHosts) {
//            String url = totalResults.getSslLabsResults().getHostName();
//            Integer reputation = totalResults.getVirusTotalResults().getCleanResultsPercentage().intValue();
//            Boolean compliance = totalResults.getSslLabsResults().isTlsInGoodShape();
//            List<String> headers = totalResults.getSecurityHeadersResults().getRedHeaders();
//            Boolean isCertificateOk = totalResults.getSslLabsResults().isSummaryCertificateValid();
//            Integer protocolPercents = totalResults.getSslLabsResults().getSummaryProtocolSupport().intValue();
//            Integer keyPercents = totalResults.getSslLabsResults().getSummaryKeyRating().intValue();
//            Integer cipherPercents = totalResults.getSslLabsResults().getSummaryCipher().intValue();
//            Boolean isIntegrity = totalResults.getSslLabsResults().isRc4Supported();
//
//
//            PDDocument doc = new PdfReport.PdfReportBuilder(url)
//                    .reputation(reputation)
//                    .compliance(compliance)
//                    .securityWeb(headers, SecurityHeadersResults.MAX_RED_HEADERS)
//                    .confidentiality(isCertificateOk, protocolPercents, keyPercents, cipherPercents)
//                    .integrity(isIntegrity)
//                    .build()
//                    .getDocument();
            try {
                doc.save("D:/report-" + URLEncoder.encode(url, "UTF-8") + ".pdf");
                doc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
//        }
    }
}
