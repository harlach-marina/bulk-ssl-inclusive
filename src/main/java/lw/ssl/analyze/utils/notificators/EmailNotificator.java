package lw.ssl.analyze.utils.notificators;

import lw.ssl.analyze.utils.PropertyFilesHelper;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONObject;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.ServletContext;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Created by zmushko_m on 21.04.2016.
 */
public class EmailNotificator {
    private static final String EMAIL_PROPERTIES_SERVLET_CONTENT_PATH = "/WEB-INF/properties/email.properties";
    public static final String RESULTS = "results";
    public static final String PATTERN = "yyyy.MM.dd_HH.mm.ss";

    public static void notificate(Workbook report, String subject, ServletContext servletContext) {
        if (report != null) {
            try {
                final Properties props = PropertyFilesHelper.getPropertyByPath(EMAIL_PROPERTIES_SERVLET_CONTENT_PATH, servletContext);

                if (!props.isEmpty()) {
                    Session session = Session.getDefaultInstance(props,
                            new javax.mail.Authenticator() {
                                protected PasswordAuthentication getPasswordAuthentication() {
                                    return new PasswordAuthentication(props.getProperty("mail.user"), props.getProperty("mail.password"));
                                }
                            });

                    Message msg = new MimeMessage(session);
                    msg.setFrom(new InternetAddress(props.getProperty("mail.from")));

                    String mailToEMails = props.getProperty("mail.to");
                    String[] eMails = mailToEMails.split(",");
                    InternetAddress[] internetAddresses = new InternetAddress[eMails.length];
                    for (int i = 0; i < eMails.length; i++) {
                        internetAddresses[i] = new InternetAddress(eMails[i].trim());
                    }

                    MimeBodyPart attachmentPart = new MimeBodyPart();

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    report.write(byteArrayOutputStream);
                    report.close();

                    attachmentPart = new MimeBodyPart();
                    attachmentPart.setContent(byteArrayOutputStream.toByteArray(), "application/vnd.ms-excel");
                    attachmentPart.setFileName(RESULTS + "_" + new SimpleDateFormat(PATTERN).format(new Date()) + ".xls");
                    attachmentPart.setDisposition(MimeBodyPart.ATTACHMENT);

                    Multipart multipart = new MimeMultipart();
                    multipart.addBodyPart(attachmentPart);
                    msg.setContent(multipart);

                    msg.addRecipients(Message.RecipientType.TO, internetAddresses);
                    if (subject != null && subject != "") {
                        msg.setSubject(subject);
                    }

                    msg.saveChanges();
                    Transport.send(msg);
                }
            } catch (AddressException e) {
                e.printStackTrace();
            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
