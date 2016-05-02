package lw.ssl.analyze.report;

import api.lw.ssl.analyze.*;
import api.lw.ssl.analyze.enums.HostAssessmentStatus;
import api.lw.ssl.analyze.enums.Protocol;
import api.lw.ssl.analyze.enums.WeakKeyDebian;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by zmushko_m on 28.04.2016.
 */
public class ExcelReportBuilder {
    private final static String EXCEL_TEMPLATE_FILE_PATH = "/templates/report-remplate.xlt";
    private static final String YES = "Yes";
    private static final String NO = "No";
    private static final String INSECURE = "Insecure";

    public static String ENDPOINTS_NAME = "endpoints";

    public static Workbook buildReport(List<JSONObject> analyzedHosts, ServletContext servletContext) {
        Workbook report = null;
        try {
            POIFSFileSystem fs = new POIFSFileSystem(servletContext.getResourceAsStream(EXCEL_TEMPLATE_FILE_PATH));
            report = new HSSFWorkbook(fs, true);

            Sheet sheetSuccess = report.getSheetAt(0);
            Sheet sheetUnsuccess = report.getSheetAt(1);

            int currentRowSuccess = 3;
            int currentRowUnsuccess = 2;

            for (int i = 0; i < analyzedHosts.size(); i++ ) {
                final JSONObject analyzedHost = analyzedHosts.get(i);

                Host host = new Host(analyzedHost);

                if (HostAssessmentStatus.READY.equals(host.getStatus())) {
                    //Successful assessments

                    if (analyzedHost.has(ENDPOINTS_NAME)) {
                        JSONArray endpoints = analyzedHost.getJSONArray(ENDPOINTS_NAME);

                        if (endpoints.length() > 0) {

                            for (int endpointsPointer = 0; endpointsPointer < endpoints.length(); endpointsPointer++) {
                                //Every endpoint new row
                                Row row = sheetSuccess.createRow(currentRowSuccess++);

                                Endpoint endpoint = new Endpoint(endpoints.getJSONObject(endpointsPointer));

                                //Host
                                Cell hostNameCell = row.createCell(0);
                                hostNameCell.setCellValue(host.getHost());

                                //Port
                                Cell hostPortCell = row.createCell(1);
                                hostPortCell.setCellValue(host.getPort());

                                //IP address
                                Cell ipAddressCell = row.createCell(2);
                                ipAddressCell.setCellValue(endpoint.getIpAddress());

                                //Grade
                                Cell statusMessageCell = row.createCell(3);
                                statusMessageCell.setCellValue(endpoint.getStatusMessage());

                                //Status message
                                Cell gradeCell = row.createCell(4);
                                String grade = endpoint.getGrade();
                                gradeCell.setCellValue(endpoint.getGrade());

                                if (StringUtils.isNotBlank(grade)) {
                                    CellStyle headerStyle = report.createCellStyle();
                                    Font headerFont = report.createFont();

                                    if ("A".equals(grade) || "A-".equals(grade) || "A+".equals(grade)) {
                                        headerStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
                                    } else if ("F".equals(grade) || "T".equals(grade)) {
                                        headerStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
                                    } else {
                                        headerStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
                                    }

                                    headerFont.setColor(IndexedColors.WHITE.getIndex());
                                    headerFont.setBold(true);
                                    headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
                                    headerStyle.setFont(headerFont);

                                    gradeCell.setCellStyle(headerStyle);
                                }

                                //Certificate section beginning
                                    //Subject
                                    Cell gradeSubjectCell = row.createCell(5);

                                    EndpointDetails details = endpoint.getDetails();

                                    if (details != null) {
                                        Cert cert = details.getCert();

                                        gradeSubjectCell.setCellValue(cert.getSubject());

                                        //Common names
                                        List<String> commonNames = cert.getCommonNames();
                                        if (commonNames != null) {
                                            Cell commonNamesCell = row.createCell(6);
                                            commonNamesCell.setCellValue(StringUtils.join(commonNames, ", "));
                                        }

                                        //Alternative names
                                        List<String> alternativeNames = cert.getAlternativeNames();
                                        if (alternativeNames != null) {
                                            Cell alternativeNamesCell = row.createCell(7);
                                            alternativeNamesCell.setCellValue(StringUtils.join(alternativeNames, ", "));
                                        }

                                        SimpleDateFormat dateFormat = new SimpleDateFormat("d.MM.yyyy");
                                        //Valid from
                                        Calendar validFromCalendar = cert.getValidFromDate();
                                        if (validFromCalendar != null) {
                                            Cell validFromCell = row.createCell(8);
                                            validFromCell.setCellValue(dateFormat.format(validFromCalendar.getTime()));
                                        }

                                        //Valid until
                                        Calendar validUntilCalendar = cert.getValidUntilDate();
                                        if (validUntilCalendar != null) {
                                            Cell validUntilCell = row.createCell(9);
                                            validUntilCell.setCellValue(dateFormat.format(validUntilCalendar.getTime()));
                                        }

                                        Key key = details.getKey();

                                        //Key
                                        Cell keyCell = row.createCell(10);
                                        keyCell.setCellValue(MessageFormat.format("{0} {1}", key.getKey(), key.getKeyStrength()));

                                        //Weak key (Debian)
                                        Cell weakKeyCell = row.createCell(11);
                                        WeakKeyDebian weakKeyDebian = key.getWeakKeyDebian();
                                        if (weakKeyDebian != null) {
                                            weakKeyCell.setCellValue(weakKeyDebian.getValue());
                                        }

                                        //Issuer
                                        Cell issuerCell = row.createCell(12);
                                        issuerCell.setCellValue(cert.getIssuer());

                                        //Signature algorithm
                                        Cell signatureAlgorithmCell = row.createCell(13);
                                        signatureAlgorithmCell.setCellValue(cert.getSignatureAlgorithm());

                                        //Extended Validation
                                        Cell extendedValidationCell = row.createCell(14);
                                        extendedValidationCell.setCellValue(cert.getExtendedValidation().getCode());

                                        //Revocation information
                                        Cell revocationInformationCell = row.createCell(15);
                                        revocationInformationCell.setCellValue(cert.getRevocationInfo().getValue());

                                        //Revocation status
                                        Cell revocationStatusCell = row.createCell(16);
                                        revocationStatusCell.setCellValue(cert.getRevocationStatus().getName());
                                    }
                                //Certificate section ending

                                //Configuration section beginning
                                    //Protocols beginning
                                    ProtocolContainer protocolContainer = details.getProtocols();
                                    //TLS_1.0
                                    addProtocolInfo(row, 17, protocolContainer, Protocol.TLS_1_0);
                                    //TLS_1.1
                                    addProtocolInfo(row, 18, protocolContainer, Protocol.TLS_1_1);
                                    //TLS_1.2
                                    addProtocolInfo(row, 19, protocolContainer, Protocol.TLS_1_2);
                                    //SSL_2
                                    addProtocolInfo(row, 20, protocolContainer, Protocol.SSL_2);
                                    //SSL_3
                                    addProtocolInfo(row, 21, protocolContainer, Protocol.SSL_3);
                                    //Protocols ending
                                //Configuration section ending
                            }


                        }
                    }
                } else {
                    //Unsuccessful assessments
                    Row row = sheetUnsuccess.createRow(currentRowUnsuccess++);

                    //Host
                    Cell hostNameCell = row.createCell(0);
                    hostNameCell.setCellValue(host.getHost());

                    //Port
                    Cell hostPortCell = row.createCell(1);
                    hostPortCell.setCellValue(host.getPort());

                    //Error message
                    Cell statusMessageCell = row.createCell(2);
                    statusMessageCell.setCellValue(host.getStatusMessage());
                }
            }

            //Autosize width columns into successful assessments
            Row successRow = sheetSuccess.getRow(2);
            if (successRow != null) {
                for (int columnNum = 0; columnNum < successRow.getLastCellNum(); columnNum++ ) {
                    sheetSuccess.autoSizeColumn(columnNum);
                }
            }

            //Autosize width columns into unsuccessful assessments
            Row unsuccessRow = sheetUnsuccess.getRow(1);
            if (unsuccessRow != null) {
                for (int columnNum = 0; columnNum < unsuccessRow.getLastCellNum(); columnNum++) {
                    sheetUnsuccess.autoSizeColumn(columnNum);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return report;
    }

    private static void addProtocolInfo(Row row, int cellNum, ProtocolContainer protocolContainer, Protocol protocol) {
        Cell protocolCell = row.createCell(cellNum);
        StringBuffer protocolString = new StringBuffer();
        ProtocolDetails protocolDetails = protocolContainer.getProtocolDetailByNameAndVersion(protocol.getName(), protocol.getVersion());
        if (protocolDetails.isAvailible()) {
            protocolString.append(YES);
        } else {
            protocolString.append(NO);
        }

        if (protocolDetails.isInsecure()) {
            protocolString.append(MessageFormat.format(" {0}", INSECURE));
        }
        protocolCell.setCellValue(protocolString.toString());
    }
}
