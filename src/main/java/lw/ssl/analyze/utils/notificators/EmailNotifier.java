package lw.ssl.analyze.utils.notificators;

import lw.ssl.analyze.utils.PropertyFilesHelper;
import org.apache.commons.lang3.StringUtils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * Created by zmushko_m on 21.04.2016.
 */
public class EmailNotifier {
    private static final String EMAIL_PROPERTIES_SERVLET_CONTENT_PATH = "/properties/email.properties";
    private static final String RESULTS = "results";
    private static final String PATTERN = "yyyy.MM.dd_HH.mm.ss";

    public static  void notifyWithAttachment(ByteArrayOutputStream attachments, FileExtension fileExtension,
                                             String subject, String mailTo) {
        if (StringUtils.isNotBlank(mailTo)) {
            notify(subject, mailTo, null, attachments, fileExtension, false);
        } else {
            System.out.println("Excel report wasn't sent - report or receiver is null");
        }
    }

    public static void notifyAdminWithMessage(String message, String subject) {
        if (StringUtils.isNotBlank(message)) {
            notify(subject, null, message, null, null, true);
        } else {
            System.out.println("Message wasn't sent to e-mail - message is null");
        }
    }

    private static void notify(String subject, String mailTo, String message,
                               ByteArrayOutputStream attachmentInBytes, FileExtension extension,
                               boolean toAdmin) {

        if (StringUtils.isNotBlank(mailTo) || toAdmin) {
            try {
                final Properties props = PropertyFilesHelper.getPropertyByPath(EMAIL_PROPERTIES_SERVLET_CONTENT_PATH);

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

                    if (attachmentInBytes != null) {

//                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                        attachmentInBytes.write(byteArrayOutputStream);
//                        attachmentInBytes.close();

                        MimeBodyPart attachmentPart = new MimeBodyPart();

                        attachmentPart.setContent(attachmentInBytes.toByteArray(), "application/vnd.ms-excel");
                        attachmentPart.setFileName(RESULTS + "_" + new SimpleDateFormat(PATTERN).format(new Date()) +
                                extension.getValue());
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
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }
}
