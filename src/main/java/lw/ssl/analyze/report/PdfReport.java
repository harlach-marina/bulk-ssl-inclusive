package lw.ssl.analyze.report;

import lw.ssl.analyze.pojo.dnssec.DnsSecAnalyzerResults;
import lw.ssl.analyze.pojo.securityheaders.SecurityHeadersResults;
import lw.ssl.analyze.pojo.ssllabs.SslLabsResults;
import lw.ssl.analyze.pojo.tlscheck.TlsCheckResults;
import lw.ssl.analyze.pojo.virustotal.VirusTotalResults;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import java.awt.*;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created on 12.10.2016;
 *
 * @author p.sinitskiy (adronex303@gmail.com);
 * @since 1.0.
 */
public class PdfReport {

    private static PDDocument doc = new PDDocument();

    private PdfReport(PdfReportBuilder builder) {
        try {
            doc = new DocumentBuilder()
                    .title(builder.siteUrl)
                    .summaryBar()
                    .reputation(builder.reputationPercentage)
                    .communication(builder.communicationTlsAdvPercentage,
                            builder.communicationCertOkPercentage,
                            builder.communicationTlsNegPercentage)
                    .compliance(builder.isCompliant)
                    .securityWeb(builder.securityWebBadHeaders, builder.securityWebPercentage)
                    .confidentiality(builder.confidentialityCertificateValidity,
                            builder.confidentialityProtocolPercentage,
                            builder.confidentialityKeyPercentage,
                            builder.confidentialityCipherPercentage)
                    .integrity(builder.isIntegral)
                    .securityDns(builder.securityDnsPercentage, builder.dnsSecAnalyzerRedResults)
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PDDocument getDocument() {
        return doc;
    }

    public static class PdfReportBuilder {
        private final String siteUrl;
        private Integer reputationPercentage;

        private Integer communicationTlsAdvPercentage;
        private Integer communicationCertOkPercentage;
        private Integer communicationTlsNegPercentage;

        private Boolean isCompliant;

        private List<String> securityWebBadHeaders;
        private Integer securityWebPercentage;

        private Set<String> dnsSecAnalyzerRedResults;
        private Integer securityDnsPercentage;

        private Boolean confidentialityCertificateValidity;
        private Integer confidentialityProtocolPercentage;
        private Integer confidentialityKeyPercentage;
        private Integer confidentialityCipherPercentage;

        private Boolean isIntegral;

        public PdfReportBuilder(String siteUrl) {
            this.siteUrl = siteUrl;
        }

        public PdfReportBuilder sslLabsResults(SslLabsResults sslLabsResults) {
            isCompliant = sslLabsResults.isTlsInGoodShape();
            confidentialityCertificateValidity = sslLabsResults.isSummaryCertificateValid();
            confidentialityProtocolPercentage = sslLabsResults.getSummaryProtocolSupport().intValue();
            confidentialityKeyPercentage = sslLabsResults.getSummaryKeyRating().intValue();
            confidentialityCipherPercentage = sslLabsResults.getSummaryCipher().intValue();
            isIntegral = sslLabsResults.isRc4Supported();
            return this;
        }

        public PdfReportBuilder tlsCheckResults(TlsCheckResults tlsCheckResults) {
            communicationTlsAdvPercentage = tlsCheckResults.getTlsAdvPercents();
            communicationCertOkPercentage = tlsCheckResults.getCertOkPercents();
            communicationTlsNegPercentage = tlsCheckResults.getTlsNegPercents();
            return this;
        }

        public PdfReportBuilder virusTotalResults(VirusTotalResults virusTotalResults) {
            reputationPercentage = virusTotalResults.getCleanResultsPercentage().intValue();
            return this;
        }

        public PdfReportBuilder securityHeadersResults(SecurityHeadersResults securityHeadersResults) {
            securityWebBadHeaders = securityHeadersResults.getRedHeaders();
            securityWebPercentage = securityHeadersResults.getGreenHeaders().isEmpty() ? 15 :
                    100 - (100 / SecurityHeadersResults.MAX_RED_HEADERS * securityHeadersResults.getRedHeaders().size());
            return this;
        }

        public PdfReportBuilder dnsSecAnalyzerResults(DnsSecAnalyzerResults dnsSecAnalyzerResults) {
            dnsSecAnalyzerRedResults = dnsSecAnalyzerResults.getRedResults();
            securityDnsPercentage = dnsSecAnalyzerResults.getPercentageOfGreenResults();
            return this;
        }

        public PdfReport build() {
            return new PdfReport(this);
        }
    }

    private static class DocumentBuilder {
        private PDDocument doc;
        private PDFont paragraphFont;
        private static final Integer NEXT_PAGE_THRESHOLD = 250;
        private static final Integer PARAGRAPH_FONT_SIZE = 14;

        private static final Integer PARAGRAPH_OFFSET_X = 70;
        private static final Integer PARAGRAPH_OFFSET_Y = 50;

        private PDFont detailsFont;
        private PDFont detailsBoldFont;
        private PDFont warningFont;
        private static final Integer DETAILS_FONT_SIZE = 8;
        private static final Integer DETAILS_OFFSET_X = 130;
        private static final Integer DETAILS_OFFSET_Y = 20;
        private static final Integer DETAILS_SMALL_OFFSET_Y = 10;

        private static final Integer SUMMARY_BAR_HEIGHT = 120;
        private static final Integer SUMMARY_BAR_WIDTH = 350;
        private static final Float SUMMARY_BAR_LINES_WIDTH = 0.3F;
        private static final Integer SUMMARY_BAR_LINES_COUNT = 10;
        private static final Integer SUMMARY_METRIC_MARGIN = 18;
        private static final Integer SUMMARY_BAR_FONT_SIZE = 6;

        private static final Float PERCENTAGE_LINE_HEIGHT = 10F;
        private static final Float PERCENTAGE_LINE_WIDTH = 380F;

        private static final Color COLOR_BLACK = new Color(4, 4, 4);
        private static final Color COLOR_VERY_LIGHT_GRAY = new Color(246, 246, 246);
        private static final Color COLOR_LIGHT_GRAY = new Color(237, 237, 237);
        private static final Color COLOR_GRAY = new Color(192, 192, 192);
        private static final Color COLOR_RED = new Color(242, 62, 83);
        private static final Color COLOR_YELLOW = new Color(255, 230, 23);
        private static final Color COLOR_ORANGE = new Color(255, 165, 24);
        private static final Color COLOR_GREEN = new Color(77, 186, 5);

        private PDPageContentStream content;
        private Integer pageHeight;
        private Integer pageWidth;
        private Integer currentOffsetY;

        private Boolean isSummaryBarBuilt = false;
        private PDPageContentStream summaryBarContent;
        private Map<String, Integer> statisticsEntries = new LinkedHashMap<>();
        private Integer summaryBarOffsetY;

        private DocumentBuilder() {
            try {
                doc = new PDDocument();
                setActualContent();
                paragraphFont = PDType0Font.load(doc, DocumentBuilder.class.getResourceAsStream("/fonts/Bitter-Regular.ttf"));
                detailsFont = PDType0Font.load(doc, DocumentBuilder.class.getResourceAsStream("/fonts/Lato-Regular.ttf"));
                detailsBoldFont = PDType0Font.load(doc, DocumentBuilder.class.getResourceAsStream("/fonts/Lato-Bold.ttf"));
                warningFont = PDType0Font.load(doc, DocumentBuilder.class.getResourceAsStream("/fonts/ufonts.com_segoe-ui-symbol.ttf"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private DocumentBuilder title(String urlTitle) throws IOException {
            if (urlTitle == null) {
                return this;
            }
            currentOffsetY -= PARAGRAPH_OFFSET_Y;
            Float titleWidth = paragraphFont.getStringWidth(urlTitle) / 1000
                    * PARAGRAPH_FONT_SIZE;
            Float titleHeight = paragraphFont.getFontDescriptor().getFontBoundingBox().getHeight() / 1000
                    * PARAGRAPH_FONT_SIZE;
            Integer xOffset = (pageWidth - titleWidth.intValue()) / 2;
            Integer yOffset = currentOffsetY - titleHeight.intValue();
            appendTextWithoutNewLine(urlTitle, paragraphFont, PARAGRAPH_FONT_SIZE, xOffset, yOffset);
            setActualContent();
            return this;
        }

        private DocumentBuilder summaryBar() throws IOException {
            // Write status bar title
            String title = "Digital Security Scanner Measurement";
            currentOffsetY -= DETAILS_OFFSET_Y;
            Float titleWidth = detailsBoldFont.getStringWidth(title) / 1000
                    * DETAILS_FONT_SIZE;
            Float titleHeight = detailsBoldFont.getFontDescriptor().getFontBoundingBox().getHeight() / 1000
                    * DETAILS_FONT_SIZE;
            Integer xOffset = (pageWidth - titleWidth.intValue()) / 2;
            Integer yOffset = currentOffsetY - titleHeight.intValue();
            appendTextWithoutNewLine(title, detailsBoldFont, DETAILS_FONT_SIZE, xOffset, yOffset);
            // Build background
            currentOffsetY -= DETAILS_OFFSET_Y + SUMMARY_BAR_HEIGHT;
            Integer summaryBarOffsetX = (pageWidth - SUMMARY_BAR_WIDTH) / 2;
            content.setNonStrokingColor(COLOR_VERY_LIGHT_GRAY);
            content.addRect(summaryBarOffsetX, currentOffsetY, SUMMARY_BAR_WIDTH, SUMMARY_BAR_HEIGHT);
            content.fill();
            // Draw background lines
            content.setLineWidth(SUMMARY_BAR_LINES_WIDTH);
            Integer linesInterval = SUMMARY_BAR_HEIGHT / SUMMARY_BAR_LINES_COUNT;
            for (int i = 0; i < SUMMARY_BAR_LINES_COUNT; i++) {
                content.moveTo(summaryBarOffsetX - 10, currentOffsetY);
                content.setStrokingColor(i == 0 ? COLOR_GRAY : COLOR_LIGHT_GRAY);
                content.lineTo(pageWidth - summaryBarOffsetX, currentOffsetY);
                content.closeAndStroke();
                content.setStrokingColor(COLOR_BLACK);
                currentOffsetY += linesInterval;
            }
            // Draw percents scale
            for (Integer i = 100; i >= 0; i -= 10) {
                appendTextWithoutNewLine(i.toString() + "%", detailsFont, SUMMARY_BAR_FONT_SIZE, summaryBarOffsetX - 30, currentOffsetY);
                currentOffsetY -= linesInterval;
            }
            isSummaryBarBuilt = true;
            summaryBarOffsetY = currentOffsetY + linesInterval;
            summaryBarContent = content;
            setActualContent();
            return this;
        }

        private DocumentBuilder reputation(Integer percentage) throws IOException {
            if (percentage == null) {
                return this;
            }
            appendParagraphTitle("Reputation");
            appendPercentageLine(percentage);
            if (percentage <= 75) {
                appendDetailsText("Your reputation is under threat.", detailsBoldFont);
                appendDetailsTextWithSmallOffset("Search engines as Google may notify your users to avoid " +
                        "using your site because it contains malware or other threats");
            }
            statisticsEntries.put("Reputation", percentage);
            setActualContent();
            return this;
        }

        private DocumentBuilder communication(Integer tlsAdvPercents,
                                              Integer certOkPercents,
                                              Integer tlsNegPercents) throws IOException {
            if (tlsAdvPercents == null || certOkPercents == null || tlsNegPercents == null) {
                return this;
            }
            appendParagraphTitle("Communication");
            Integer communicationPercentage = (tlsAdvPercents + certOkPercents + tlsNegPercents) / 3;
            if (communicationPercentage < 15) {
                communicationPercentage = 15;
            }
            appendPercentageLine(communicationPercentage);
            if (tlsAdvPercents <= 75) {
                appendDetailsWarningText("TLS Adv");
                appendDetailsTextWithSmallOffset("Without TLS your data in motion such as passwords, emails, " +
                        "VoIP or credit card information as at risk of being sniffed and stolen.");
            }
            if (certOkPercents <= 75) {
                appendDetailsWarningText("Cert OK");
                appendDetailsTextWithSmallOffset("An expired certificate means there is a risk of Man in the middle " +
                        "attack, because it can no longer be trusted.");
            }
            if (tlsNegPercents <= 75) {
                appendDetailsWarningText("TLS Neg");
                appendDetailsTextWithSmallOffset("Checks to ensure Email transmission is encrypted and secure " +
                        "from eavesdroppers.");
            }
            statisticsEntries.put("Communication", communicationPercentage);
            setActualContent();
            return this;
        }

        private DocumentBuilder compliance(Boolean isCompliant) throws IOException {
            if (isCompliant == null) {
                return this;
            }
            Integer compliancePercentage = isCompliant ? 100 : 15;
            appendParagraphTitle("Compliance");
            appendPercentageLine(compliancePercentage);
            if (compliancePercentage <= 75) {
                appendDetailsText("For your business to be PCI Compliant, you need to stop running SSL 2.0, " +
                        "SSL 3.0 or TLS 1.0. Reconfigure or update to TLS 1.2.");
            }
            statisticsEntries.put("Compliance", compliancePercentage);
            setActualContent();
            return this;
        }

        private DocumentBuilder securityWeb(List<String> redHeaders, Integer percentage) throws IOException {
            if (redHeaders == null || percentage == null) {
                return this;
            }
            appendParagraphTitle("Security (WEB)");
            appendPercentageLine(percentage);
            for (String s : redHeaders) {
                appendDetailsWarningText(s);
                if ("X-XSS-Protection".equals(s)) {
                    appendDetailsTextWithSmallOffset("X-XSS-Protection sets the configuration for the cross-site " +
                            "scripting filter built into most browsers. ");
                    appendDetailsTextWithSmallOffset("Recommended value \"X-XSS-Protection: 1;mode=block\".");
                } else {
                    appendDetailsTextWithSmallOffset(getHeaderComment(s));
                }
            }
            statisticsEntries.put("Security (Web)", percentage);
            setActualContent();
            return this;
        }

        private DocumentBuilder confidentiality(Boolean certificateValidity,
                                                Integer protocolPercentage,
                                                Integer keyPercentage,
                                                Integer cipherPercentage) throws IOException {
            if (certificateValidity == null || protocolPercentage == null
                    || keyPercentage == null || cipherPercentage == null) {
                return this;
            }
            appendParagraphTitle("Confidentiality");
            Integer certificatePercentage = certificateValidity ? 100 : 15;
            appendPercentageLine(certificatePercentage);
            appendTextWithoutNewLine("Certificate", detailsBoldFont, DETAILS_FONT_SIZE, PARAGRAPH_OFFSET_X, currentOffsetY);
            appendPercentageLine(protocolPercentage);
            appendTextWithoutNewLine("Protocol Support", detailsBoldFont, DETAILS_FONT_SIZE, PARAGRAPH_OFFSET_X, currentOffsetY);
            appendPercentageLine(keyPercentage);
            appendTextWithoutNewLine("Key Exchange", detailsBoldFont, DETAILS_FONT_SIZE, PARAGRAPH_OFFSET_X, currentOffsetY);
            appendPercentageLine(cipherPercentage);
            appendTextWithoutNewLine("Cipher Strength", detailsBoldFont, DETAILS_FONT_SIZE, PARAGRAPH_OFFSET_X, currentOffsetY);
            if (!certificateValidity || protocolPercentage <= 75 || keyPercentage <= 75 || cipherPercentage <= 75) {
                currentOffsetY -= DETAILS_OFFSET_Y;
            }
            if (!certificateValidity) {
                appendDetailsTextWithSmallOffset("Your site uses expired or not trusted certificate.");
            }
            if (protocolPercentage <= 75) {
                appendDetailsTextWithSmallOffset("Your site support old or nonsecure protocols");
            }
            if (keyPercentage <= 75) {
                appendDetailsTextWithSmallOffset("Your site is insecure as a result of SSL handshake failure.");
            }
            if (cipherPercentage <= 75) {
                appendDetailsTextWithSmallOffset("Your site is not using strong cipher.");
            }
            Integer averageRating = (certificatePercentage + protocolPercentage + keyPercentage + cipherPercentage) / 4;
            statisticsEntries.put("Confidentiality", averageRating);
            setActualContent();
            return this;
        }

        private DocumentBuilder integrity(Boolean isIntegral) throws IOException {
            if (isIntegral == null) {
                return this;
            }
            appendParagraphTitle("Integrity");
            Integer percentage = isIntegral ? 100 : 10;
            appendPercentageLine(percentage);
            if (percentage <= 75) {
                appendDetailsText("Your site is not using strong cipher");
            }
            statisticsEntries.put("Integrity", percentage);
            setActualContent();
            return this;
        }

        private DocumentBuilder securityDns(Integer percentage, Set<String> badResults) throws IOException {
            if (percentage == null || badResults == null) {
                return this;
            }
            appendParagraphTitle("Security (DNS)");
            appendPercentageLine(percentage);
            if (percentage <= 75) {
                appendDetailsText("DNSSEC records prevent attackers from falsifying DNS records that ensure the ");
                appendDetailsTextWithSmallOffset("integrity of a domain.");
            }
            statisticsEntries.put("Security (DNS)", percentage);
            return this;
        }

        PDDocument build() throws IOException {
            if (isSummaryBarBuilt) {
                content.close();
                content = summaryBarContent;
                Integer summaryBarOffsetX = (pageWidth - SUMMARY_BAR_WIDTH) / 2;
                currentOffsetY = summaryBarOffsetY;
                Integer currentOffsetX = summaryBarOffsetX + SUMMARY_METRIC_MARGIN / 2;
                Integer barWidth = SUMMARY_BAR_WIDTH / statisticsEntries.size() - SUMMARY_METRIC_MARGIN;
                for (Map.Entry<String, Integer> entry : statisticsEntries.entrySet()) {
                    content.setNonStrokingColor(getColorByPercentage(entry.getValue()));
                    Float barHeight = SUMMARY_BAR_HEIGHT / 100F * entry.getValue();
                    content.addRect(currentOffsetX, currentOffsetY, barWidth, barHeight);
                    currentOffsetY -= DETAILS_SMALL_OFFSET_Y;
                    Float titleWidth = paragraphFont.getStringWidth(entry.getKey()) / 1000
                            * SUMMARY_BAR_FONT_SIZE;
                    Float titleOffset = currentOffsetX - titleWidth / 2 + barWidth / 2 + 4; // +4 for better centering
                    content.fill();
                    appendTextWithoutNewLine(entry.getKey(), detailsFont, SUMMARY_BAR_FONT_SIZE, titleOffset.intValue(), currentOffsetY);
                    currentOffsetY += DETAILS_SMALL_OFFSET_Y;
                    currentOffsetX += barWidth + SUMMARY_METRIC_MARGIN;
                }
                content.close();
            }
            return doc;
        }

        private void setActualContent() throws IOException {

            if (currentOffsetY == null || currentOffsetY < NEXT_PAGE_THRESHOLD) {
                if (content != null) {
                    content.close();
                }
                PDPage page = new PDPage();
                doc.addPage(page);
                content = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, false);
                pageHeight = Math.round(page.getMediaBox().getHeight());
                pageWidth = Math.round(page.getMediaBox().getWidth());
                currentOffsetY = pageHeight;
            }
        }

        private void appendParagraphTitle(String title) throws IOException {
            currentOffsetY -= PARAGRAPH_OFFSET_Y;
            appendTextWithoutNewLine(title, paragraphFont, PARAGRAPH_FONT_SIZE, PARAGRAPH_OFFSET_X, currentOffsetY);
        }

        private void appendDetailsText(String text, PDFont font) throws IOException {
            currentOffsetY -= DETAILS_OFFSET_Y;
            appendTextWithoutNewLine(text, font, DETAILS_FONT_SIZE, DETAILS_OFFSET_X, currentOffsetY);
        }

        private void appendDetailsWarningText(String text) throws IOException {
            currentOffsetY -= DETAILS_OFFSET_Y;
            appendTextWithoutNewLine("" + '\u26a0', warningFont, DETAILS_FONT_SIZE, DETAILS_OFFSET_X - 10,
                    currentOffsetY, COLOR_RED);
            appendTextWithoutNewLine(text, detailsBoldFont, DETAILS_FONT_SIZE, DETAILS_OFFSET_X, currentOffsetY,
                    COLOR_RED);
        }

        private void appendDetailsTextWithSmallOffset(String text) throws IOException {
            currentOffsetY -= DETAILS_SMALL_OFFSET_Y;
            appendTextWithoutNewLine(text, detailsFont, DETAILS_FONT_SIZE, DETAILS_OFFSET_X, currentOffsetY);
        }

        private void appendDetailsText(String text) throws IOException {
            appendDetailsText(text, detailsFont);
        }

        private void appendTextWithoutNewLine(String text, PDFont font,
                                              Integer fontSize, Integer offsetX, Integer offsetY)
                throws IOException {
            appendTextWithoutNewLine(text, font, fontSize, offsetX, offsetY, COLOR_BLACK);
        }

        private void appendTextWithoutNewLine(String text, PDFont font,
                                              Integer fontSize, Integer offsetX, Integer offsetY, Color color)
                throws IOException {
            content.beginText();
            content.setNonStrokingColor(color);
            content.setFont(font, fontSize);
            content.newLineAtOffset(offsetX, offsetY);
            content.showText(text);
            content.endText();
        }

        private void appendPercentageLine(Integer percentage) throws IOException {
            currentOffsetY -= DETAILS_OFFSET_Y;
            Integer offsetY = currentOffsetY - 2;
            content.setNonStrokingColor(COLOR_LIGHT_GRAY);
            content.addRect(DETAILS_OFFSET_X, offsetY, PERCENTAGE_LINE_WIDTH, PERCENTAGE_LINE_HEIGHT);
            content.fill();
            content.setNonStrokingColor(getColorByPercentage(percentage));
            content.addRect(DETAILS_OFFSET_X, offsetY, percentage * PERCENTAGE_LINE_WIDTH / 100, PERCENTAGE_LINE_HEIGHT);
            content.fill();
            content.closeAndStroke();
            content.close();
        }

        private String getHeaderComment(String header) {
            switch (header) {
                case "Content-Security-Policy":
                    return "Your site is not protect from XSS attacks. You need to prevent user browser from " +
                            "loading malicious contents.";
                case "X-Frame-Options":
                    return "Your site isn't defended against attacks like clickjacking.";
                case "X-XSS-Protection":
                    return "X-XSS-Protection sets the configuration for the cross-site scripting filter built into " +
                            "most browsers. Recommended value \"X-XSS-Protection: 1;mode=block\".";
                case "X-Content-Type-Options":
                    return "Your site isn't preventing an attacker from typing to MIME-sniff the content.";
            }
            return "We have no info about this header. Check it on www.securityheaders.com .";
        }

        private Color getColorByPercentage(Integer percentage) {
            return percentage > 25
                    ? percentage > 50
                    ? percentage > 75
                    ? COLOR_GREEN : COLOR_ORANGE : COLOR_YELLOW : COLOR_RED;
        }
    }
}
