package lw.ssl.analyze.report;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 12.10.2016;
 *
 * @author p.sinitskiy (adronex303@gmail.com);
 * @since 1.0.
 */
public class PdfReport {

    private PDDocument doc = new PDDocument();

    private PdfReport(PdfReportBuilder builder) {
        doc = new PDDocument();
        PDPage page = new PDPage();
        doc.addPage(page);

        try {
            Map<String, Integer> summaryEntries = new LinkedHashMap <>();
            summaryEntries.put("Reputation", builder.reputationPercentage);
            summaryEntries.put("Compliance", builder.compliancePercentage);
            summaryEntries.put("Security (Web)",
                    100 / builder.securityWebTotalBadHeaders * builder.securityWebBadHeaders.size());
            Integer confidentialityPercentage = ((builder.confidentialityCertificateValidity ? 100 : 0) +
                    builder.confidentialityProtocolPercentage +
                    builder.confidentialityKeyPercentage +
                    builder.confidentialityCipherPercentage) / 4;
            summaryEntries.put("Confidentiality", confidentialityPercentage);
            summaryEntries.put("Integrity", builder.isIntegral ? 100 : 15);
            PDPageContentStream content = new ContentBuilder(doc, page)
                    .title(builder.siteUrl)
                    .summaryBar(summaryEntries)
                    .reputation(builder.reputationPercentage)
                    .compliance(builder.compliancePercentage)
                    .securityWeb(builder.securityWebBadHeaders, builder.securityWebTotalBadHeaders)
                    .confidentiality(builder.confidentialityCertificateValidity,
                            builder.confidentialityProtocolPercentage,
                            builder.confidentialityKeyPercentage,
                            builder.confidentialityCipherPercentage)
                    .integrity(builder.isIntegral)
                    .build();
            content.close();
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
        private Integer compliancePercentage;
        private List<String> securityWebBadHeaders;
        private Integer securityWebTotalBadHeaders;

        private Boolean confidentialityCertificateValidity;
        private Integer confidentialityProtocolPercentage;
        private Integer confidentialityKeyPercentage;
        private Integer confidentialityCipherPercentage;

        private Boolean isIntegral;

        public PdfReportBuilder(String siteUrl) {
            this.siteUrl = siteUrl;
        }

        public PdfReportBuilder reputation(Integer percentage) {
            reputationPercentage = percentage == 100 ? 100 : 15;
            return this;
        }

        public PdfReportBuilder compliance(Boolean isOk) {
            compliancePercentage = isOk ? 100 : 15;
            return this;
        }

        public PdfReportBuilder securityWeb(List<String> badHeaders, Integer badHeadersTotal) {
            if (badHeaders == null) {
                badHeaders = new ArrayList<>();
            }
            securityWebBadHeaders = badHeaders;
            securityWebTotalBadHeaders = badHeadersTotal;
            return this;
        }

        public PdfReportBuilder confidentiality(Boolean isCertificateValid, Integer protocolPercentage,
                                                Integer keyPercentage, Integer cipherPercentage) {

            confidentialityCertificateValidity = isCertificateValid;
            confidentialityProtocolPercentage = protocolPercentage;
            confidentialityKeyPercentage = keyPercentage;
            confidentialityCipherPercentage = cipherPercentage;
            return this;
        }

        public PdfReportBuilder integrity(Boolean isOk) {
            isIntegral = isOk;
            return this;
        }

        public PdfReport build() {
            return new PdfReport(this);
        }
    }

    private static class ContentBuilder {
        private static final PDFont PARAGRAPH_FONT = PDType1Font.COURIER_BOLD;
        private static final Integer PARAGRAPH_FONT_SIZE = 14;

        private static final Float PARAGRAPH_OFFSET_X = 70F;
        private static final Integer PARAGRAPH_OFFSET_Y = 50;

        private static final PDFont DETAILS_FONT = PDType1Font.HELVETICA;
        private static final PDFont DETAILS_BOLD_FONT = PDType1Font.HELVETICA_BOLD;
        private static final Integer DETAILS_FONT_SIZE = 8;
        private static final Float DETAILS_OFFSET_X = 150F;
        private static final Integer DETAILS_OFFSET_Y = 20;
        private static final Integer DETAILS_SMALL_OFFSET_Y = 10;

        private static final Integer SUMMARY_BAR_HEIGHT = 120;
        private static final Integer SUMMARY_BAR_WIDTH = 300;
        private static final Float SUMMARY_BAR_LINES_WIDTH = 0.3F;
        private static final Integer SUMMARY_BAR_LINES_COUNT = 10;
        private static final Integer SUMMARY_METRIC_MARGIN = 25;
        private static final Integer SUMMARY_BAR_FONT_SIZE = 6;
        private static final PDFont SUMMARY_BAR_FONT = PDType1Font.HELVETICA;

        private static final Float PERCENTAGE_LINE_HEIGHT = 10F;
        private static final Float PERCENTAGE_LINE_WIDTH = 300F;

        private PDPageContentStream content;
        private Float pageHeight;
        private Float pageWidth;
        private Float currentOffsetY;

        private ContentBuilder(PDDocument doc, PDPage page) {
            try {
                content = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, false);
                pageHeight = page.getMediaBox().getHeight();
                pageWidth = page.getMediaBox().getWidth();
                currentOffsetY = pageHeight;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private ContentBuilder title(String urlTitle) throws IOException {
            currentOffsetY -= PARAGRAPH_OFFSET_Y;
            Float titleWidth = PARAGRAPH_FONT.getStringWidth(urlTitle) / 1000
                    * PARAGRAPH_FONT_SIZE;
            Float titleHeight = PARAGRAPH_FONT.getFontDescriptor().getFontBoundingBox().getHeight() / 1000
                    * PARAGRAPH_FONT_SIZE;
            Float xOffset = (pageWidth - titleWidth) / 2;
            Float yOffset = currentOffsetY - titleHeight;
            appendText(urlTitle, PARAGRAPH_FONT, PARAGRAPH_FONT_SIZE, xOffset, yOffset);
            return this;
        }

        private ContentBuilder summaryBar(Map<String, Integer> statisticsEntries) throws IOException {
            // Write status bar title
            String title = "Digital Security Scanner Measurement";
            currentOffsetY -= DETAILS_OFFSET_Y;
            Float titleWidth = DETAILS_BOLD_FONT.getStringWidth(title) / 1000
                    * DETAILS_FONT_SIZE;
            Float titleHeight = DETAILS_BOLD_FONT.getFontDescriptor().getFontBoundingBox().getHeight() / 1000
                    * DETAILS_FONT_SIZE;
            Float xOffset = (pageWidth - titleWidth) / 2;
            Float yOffset = currentOffsetY - titleHeight;
            appendText(title, DETAILS_BOLD_FONT, DETAILS_FONT_SIZE, xOffset, yOffset);
            // Build background
            currentOffsetY -= DETAILS_OFFSET_Y + SUMMARY_BAR_HEIGHT;
            Float summaryBarOffsetX = (pageWidth - SUMMARY_BAR_WIDTH) / 2;
            content.setNonStrokingColor(Color.LIGHT_GRAY);
            content.addRect(summaryBarOffsetX, currentOffsetY, SUMMARY_BAR_WIDTH, SUMMARY_BAR_HEIGHT);
            content.fill();
            // Draw background lines
            content.setLineWidth(SUMMARY_BAR_LINES_WIDTH);
            Integer linesInterval = SUMMARY_BAR_HEIGHT / SUMMARY_BAR_LINES_COUNT;
            for (int i = 0; i < SUMMARY_BAR_LINES_COUNT; i++) {
                content.moveTo(summaryBarOffsetX - 10, currentOffsetY);
                content.setStrokingColor(Color.DARK_GRAY);
                content.lineTo(pageWidth - summaryBarOffsetX, currentOffsetY);
                content.closeAndStroke();
                content.setStrokingColor(Color.BLACK);
                currentOffsetY += linesInterval;
            }
            // Draw percents scale
            for (Integer i = 100; i >= 0; i -= 10) {
                appendText(i.toString() + "%", SUMMARY_BAR_FONT, SUMMARY_BAR_FONT_SIZE, summaryBarOffsetX - 30, currentOffsetY);
                currentOffsetY -= linesInterval;
            }
            currentOffsetY += linesInterval;
            // Print bar titles and draw bars
            Integer currentOffsetX = summaryBarOffsetX.intValue() + SUMMARY_METRIC_MARGIN / 2;
            Integer barWidth = SUMMARY_BAR_WIDTH / statisticsEntries.size() - SUMMARY_METRIC_MARGIN;
            for (Map.Entry<String, Integer> entry : statisticsEntries.entrySet()) {
                content.setNonStrokingColor(getColorByPercentage(entry.getValue()));
                Float barHeight = SUMMARY_BAR_HEIGHT / 100F * entry.getValue();
                content.addRect(currentOffsetX, currentOffsetY, barWidth, barHeight);
                content.fill();
                currentOffsetY -= DETAILS_SMALL_OFFSET_Y;
                appendText(entry.getKey(), SUMMARY_BAR_FONT, SUMMARY_BAR_FONT_SIZE, currentOffsetX.floatValue(), currentOffsetY);
                currentOffsetY += DETAILS_SMALL_OFFSET_Y;
                currentOffsetX += barWidth + SUMMARY_METRIC_MARGIN;
            }
            return this;
        }

        private ContentBuilder reputation(Integer percentage) throws IOException {
            currentOffsetY -= PARAGRAPH_OFFSET_Y;
            appendText("Reputation", PARAGRAPH_FONT, PARAGRAPH_FONT_SIZE, PARAGRAPH_OFFSET_X, currentOffsetY);
            currentOffsetY -= DETAILS_OFFSET_Y;
            appendPercentageLine(DETAILS_OFFSET_X, currentOffsetY, percentage);
            currentOffsetY -= DETAILS_OFFSET_Y;
            if (percentage == 100) {
                appendText("Your reputation is safe.", DETAILS_FONT, DETAILS_FONT_SIZE, DETAILS_OFFSET_X,
                        currentOffsetY);
            } else {
                appendText("Your reputation is under threat.", DETAILS_BOLD_FONT, DETAILS_FONT_SIZE, DETAILS_OFFSET_X,
                        currentOffsetY);
                currentOffsetY -= DETAILS_SMALL_OFFSET_Y;
                appendText("Search engines as Google may notify your users to avoid using your site " +
                        "because it contains malware or other threats",
                        DETAILS_FONT, DETAILS_FONT_SIZE, DETAILS_OFFSET_X, currentOffsetY);
            }
            return this;
        }

        private ContentBuilder compliance(Integer compliancePercentage) throws IOException {
            currentOffsetY -= PARAGRAPH_OFFSET_Y;
            appendText("Compliance", PARAGRAPH_FONT, PARAGRAPH_FONT_SIZE, PARAGRAPH_OFFSET_X, currentOffsetY);
            currentOffsetY -= DETAILS_OFFSET_Y;
            appendPercentageLine(DETAILS_OFFSET_X, currentOffsetY, compliancePercentage);
            String details = (compliancePercentage == 100)
                    ? "Your site is PCI Compliant."
                    : "For your business to be PCI Compliant, you need to stop running SSL 2.0, SSL 3.0 or TLS 1.0. " +
                    "Reconfigure or update to TLS 1.2.";
            currentOffsetY -= DETAILS_OFFSET_Y;
            appendText(details, DETAILS_FONT, DETAILS_FONT_SIZE, DETAILS_OFFSET_X, currentOffsetY);
            return this;
        }

        private ContentBuilder securityWeb(List<String> redHeaders, Integer maxRedHeadersCount) throws IOException {
            currentOffsetY -= PARAGRAPH_OFFSET_Y;
            appendText("Security (WEB)", PARAGRAPH_FONT, PARAGRAPH_FONT_SIZE, PARAGRAPH_OFFSET_X, currentOffsetY);
            currentOffsetY -= DETAILS_OFFSET_Y;
            Integer percentage = 100 - (100 / maxRedHeadersCount * redHeaders.size());
            appendPercentageLine(DETAILS_OFFSET_X, currentOffsetY, percentage);
            if (redHeaders.isEmpty()) {
                currentOffsetY -= DETAILS_OFFSET_Y;
                appendText("Your site using all required security headers.", DETAILS_FONT, DETAILS_FONT_SIZE,
                        DETAILS_OFFSET_X, currentOffsetY);
            }
            for (String s : redHeaders) {
                currentOffsetY -= DETAILS_OFFSET_Y;
                appendText(s, DETAILS_BOLD_FONT, DETAILS_FONT_SIZE, DETAILS_OFFSET_X, currentOffsetY, Color.RED);
                currentOffsetY -= DETAILS_SMALL_OFFSET_Y;
                appendText(getHeaderComment(s), DETAILS_FONT, DETAILS_FONT_SIZE, DETAILS_OFFSET_X, currentOffsetY);
            }
            return this;
        }

        private ContentBuilder confidentiality(Boolean certificateValidity,
                                               Integer protocolPercentage,
                                               Integer keyPercentage,
                                               Integer cipherPercentage) throws IOException {
            currentOffsetY -= PARAGRAPH_OFFSET_Y;
            appendText("Confidentiality", PARAGRAPH_FONT, PARAGRAPH_FONT_SIZE, PARAGRAPH_OFFSET_X, currentOffsetY);
            currentOffsetY -= DETAILS_OFFSET_Y;
            Integer certificatePercentage = certificateValidity ? 100 : 15;
            appendPercentageLine(DETAILS_OFFSET_X, currentOffsetY, certificatePercentage);
            appendText("Certificate", DETAILS_BOLD_FONT, DETAILS_FONT_SIZE, PARAGRAPH_OFFSET_X, currentOffsetY);
            currentOffsetY -= DETAILS_OFFSET_Y;
            appendPercentageLine(DETAILS_OFFSET_X, currentOffsetY, protocolPercentage);
            appendText("Protocol Support", DETAILS_BOLD_FONT, DETAILS_FONT_SIZE, PARAGRAPH_OFFSET_X, currentOffsetY);
            currentOffsetY -= DETAILS_OFFSET_Y;
            appendPercentageLine(DETAILS_OFFSET_X, currentOffsetY, keyPercentage);
            appendText("Key Exchange", DETAILS_BOLD_FONT, DETAILS_FONT_SIZE, PARAGRAPH_OFFSET_X, currentOffsetY);
            currentOffsetY -= DETAILS_OFFSET_Y;
            appendPercentageLine(DETAILS_OFFSET_X, currentOffsetY, cipherPercentage);
            appendText("Cipher Strength", DETAILS_BOLD_FONT, DETAILS_FONT_SIZE, PARAGRAPH_OFFSET_X, currentOffsetY);
            if (!certificateValidity) {
                currentOffsetY -= DETAILS_OFFSET_Y;
                appendText("Your site uses expired or not trusted certificate.",
                        DETAILS_FONT, DETAILS_FONT_SIZE, DETAILS_OFFSET_X, currentOffsetY);
            }
            if (protocolPercentage <= 75) {
                currentOffsetY -= DETAILS_SMALL_OFFSET_Y;
                appendText("Your site support old or nonsecure protocols",
                        DETAILS_FONT, DETAILS_FONT_SIZE, DETAILS_OFFSET_X, currentOffsetY);
            }
            if (keyPercentage <= 75) {
                currentOffsetY -= DETAILS_SMALL_OFFSET_Y;
                appendText("Your site is insecure as a result of SSL handshake failure.",
                        DETAILS_FONT, DETAILS_FONT_SIZE, DETAILS_OFFSET_X, currentOffsetY);
            }
            if (cipherPercentage <= 75) {
                currentOffsetY -= DETAILS_SMALL_OFFSET_Y;
                appendText("Your site is not using strong cipher.",
                        DETAILS_FONT, DETAILS_FONT_SIZE, DETAILS_OFFSET_X, currentOffsetY);
            }
            return this;
        }

        private ContentBuilder integrity(Boolean isIntegral) throws IOException {
            currentOffsetY -= PARAGRAPH_OFFSET_Y;
            appendText("Integrity", PARAGRAPH_FONT, PARAGRAPH_FONT_SIZE, PARAGRAPH_OFFSET_X, currentOffsetY);
            currentOffsetY -= DETAILS_OFFSET_Y;
            Integer percentage = isIntegral ? 100 : 10;
            appendPercentageLine(DETAILS_OFFSET_X, currentOffsetY, percentage);
            String details = isIntegral ? "Your site is using strong cipher" : "Your site is not using strong cipher";
            currentOffsetY -= DETAILS_OFFSET_Y;
            appendText(details, DETAILS_FONT, DETAILS_FONT_SIZE, DETAILS_OFFSET_X, currentOffsetY);
            return this;
        }

        PDPageContentStream build() {
            return content;
        }

        private void appendText(String text, PDFont font,
                                Integer fontSize, Float offsetX, Float offsetY)
                throws IOException {
            appendText(text, font, fontSize, offsetX, offsetY, Color.BLACK);
        }

        private void appendText(String text, PDFont font,
                                Integer fontSize, Float offsetX, Float offsetY, Color color)
                throws IOException {
            content.beginText();
            content.setNonStrokingColor(color);
            content.setFont(font, fontSize);
            content.newLineAtOffset(offsetX, offsetY);
            content.showText(text);
            content.endText();
        }

        private void appendPercentageLine(Float offsetX, Float offsetY,
                                          Integer percentage) throws IOException {
            offsetY -= 2;
            content.setNonStrokingColor(Color.LIGHT_GRAY);
            content.addRect(offsetX, offsetY, PERCENTAGE_LINE_WIDTH, PERCENTAGE_LINE_HEIGHT);
            content.fill();
            content.setNonStrokingColor(getColorByPercentage(percentage));
            content.addRect(offsetX, offsetY, percentage * PERCENTAGE_LINE_WIDTH / 100, PERCENTAGE_LINE_HEIGHT);
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
                    ? Color.GREEN : Color.ORANGE : Color.YELLOW : Color.RED;
        }
    }
}
