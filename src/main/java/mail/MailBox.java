package mail;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.MustacheFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class MailBox {

    private JavaMailSender mailSender;
    private String emailFrom;

    private final MustacheFactory mustacheFactory = new DefaultMustacheFactory();

    public MailBox(JavaMailSender mailSender, String emailFrom) {
        this.mailSender = mailSender;
        this.emailFrom = emailFrom;
    }

    public void sendUserConfirmation(String userName, String recipient, String courseIdentifier, String courseName, String secondaryMail, String initials, String personId) throws MessagingException {
        Map<String, Object> variables = new HashMap<>();
        variables.put("userName", userName);
        variables.put("courseIdentifier", courseIdentifier);
        variables.put("courseName", courseName);
        variables.put("primaryEmail", recipient);
        variables.put("secondaryMail", secondaryMail);
        variables.put("initials", initials);
        variables.put("personId", personId);
        sendMail("confirmation", "Confirmation registered for " + courseName, variables, recipient);
    }

    private void sendMail(String templateName, String subject, Map<String, Object> variables, String to) throws MessagingException {
        String html = this.mailTemplate(String.format("mail_templates/%s.html", templateName), variables);
        String text = this.mailTemplate(String.format("mail_templates/%s.txt", templateName), variables);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setSubject(subject);
        helper.setTo(to);
        setText(html, text, helper);
        helper.setFrom(emailFrom);
        doSendMail(message);
    }

    protected void setText(String html, String text, MimeMessageHelper helper) throws MessagingException {
        helper.setText(text, html);
    }

    protected void doSendMail(MimeMessage message) {
        new Thread(() -> mailSender.send(message)).start();
    }

    private String mailTemplate(String templateName, Map<String, Object> context) {
        return mustacheFactory.compile(templateName).execute(new StringWriter(), context).toString();
    }

}
