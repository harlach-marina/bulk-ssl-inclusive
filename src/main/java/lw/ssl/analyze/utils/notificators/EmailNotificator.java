package lw.ssl.analyze.utils.notificators;

import lw.ssl.analyze.utils.PropertyFilesHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;

import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.ServletContext;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * Created by zmushko_m on 21.04.2016.
 */
public class EmailNotificator {
    public static final String EMAIL_PROPERTIES_SERVLET_CONTENT_PATH = "/WEB-INF/properties/email.properties";
    public static final String RESULTS = "results";
    public static final String PATTERN = "yyyy.MM.dd_HH.mm.ss";

    public static  void notificateWithExcelReport(Workbook report, String subject, ServletContext servletContext, String mailTo) {
        if (report != null && StringUtils.isNotBlank(mailTo)) {
            notificate(report, subject, servletContext, mailTo, null, false);
        } else {
            System.out.println("Excel report wasn't sent - report or receiver is null");
        }
    }

    public static void notificateAdminWithMessage(String message, String subject, ServletContext servletContext) {
        if (StringUtils.isNotBlank(message)) {
            notificate(null, subject, servletContext, null, message, true);
        } else {
            System.out.println("Message wasn't sent to e-mail - message is null");
        }
    }

    private static void notificate(Workbook report, String subject, ServletContext servletContext, String mailTo, String message, boolean toAdmin) {

        if (StringUtils.isNotBlank(mailTo) || toAdmin) {
            try {
                final Properties props = PropertyFilesHelper.getPropertyByPath(EMAIL_PROPERTIES_SERVLET_CONTENT_PATH, servletContext);

                if (toAdmin) {
                    mailTo = props.getProperty("mail.admin");
                }

                if (!props.isEmpty()) {
                    Session session = Session.getDefaultInstance(props,
                            new javax.mail.Authenticator() {
                                protected PasswordAuthentication getPasswordAuthentication() {
                                    return new PasswordAuthentication(props.getProperty("mail.user"), System.getenv("E_MAIL_PASS_VAR"));
                                }
                            });

                    Message msg = new MimeMessage(session);
                    msg.setFrom(new InternetAddress(props.getProperty("mail.from")));

                    if (report != null) {
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
                    }

                    msg.addRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
                    if (StringUtils.isNotBlank(subject)) {
                        msg.setSubject(subject);
                    }

                    if (StringUtils.isNotBlank(message)) {
                        msg.setText(message);
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
