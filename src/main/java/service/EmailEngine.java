package service;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.UserRepository;

import com.sun.mail.smtp.SMTPTransport;
import utils.ConstantsUtils;
import utils.EmailEngineDetails;
import utils.ServerContext;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.Properties;
import java.util.UUID;

@Service
public class EmailEngine {
    private final UserRepository userRepository;

    private final EmailEngineDetails emailEngineDetails;
    private Properties prop;

    @Autowired
    public EmailEngine(UserRepository userRepository) {
        this.userRepository = userRepository;
        emailEngineDetails = new EmailEngineDetails();

        enableProp();
    }

    private void enableProp() {
        prop = System.getProperties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.port", 587);
        prop.put("mail.smtp.starttls.enable", "true");
    }

    private static final String EMAIL_SUBJECT = "Reset your password";
    private static final String EMAIL_TEXT = "<h1>Hello Java Mail \n ABC123</h1>";

    public void sendRecoverPassowrd(final String email) {
        Optional<User> optionalUser = userRepository.findFirstByEmail(email);
        optionalUser.ifPresent(u -> {
            final String token = String.format("%s%s%s", UUID.randomUUID().toString().toUpperCase(), u.getUsername(), LocalDateTime.now().toString());
            u.setRecoverPassowrdCode(token);
            userRepository.save(u);
        });
    }

    public void sendEmail(final String email) {
        Session session = Session.getInstance(prop, null);
        Message msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(emailEngineDetails.getUsername()));
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email, false));
            msg.setSubject(EMAIL_SUBJECT);

            // HTML email
            msg.setDataHandler(new DataHandler(new HTMLDataSource(EMAIL_TEXT)));

            SMTPTransport t = (SMTPTransport) session.getTransport("smtp");

            // connect
            t.connect(emailEngineDetails.getSmtpServer(), emailEngineDetails.getUsername(), emailEngineDetails.getPassword());

            // send
            t.sendMessage(msg, msg.getAllRecipients());
            System.out.println("Response: " + t.getLastServerResponse());
            t.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    static class HTMLDataSource implements DataSource {

        private String html;

        public HTMLDataSource(String htmlString) {
            html = htmlString;
        }

        @Override
        public InputStream getInputStream() throws IOException {
            if (html == null) throw new IOException("html message is null!");
            return new ByteArrayInputStream(html.getBytes());
        }

        @Override
        public OutputStream getOutputStream() throws IOException {
            throw new IOException("This DataHandler cannot write HTML");
        }

        @Override
        public String getContentType() {
            return "text/html";
        }

        @Override
        public String getName() {
            return "HTMLDataSource";
        }
    }
}