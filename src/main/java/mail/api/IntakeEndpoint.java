package mail.api;

import lombok.SneakyThrows;
import mail.MailBox;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class IntakeEndpoint {

    private static final Log LOG = LogFactory.getLog(IntakeEndpoint.class);

    private final MailBox mailBox;

    private final boolean delayEnabled;
    private final long delayMillis;

    public IntakeEndpoint(MailBox mailBox,
                          @Value("${delay.enabled}") boolean delayEnabled,
                          @Value("${delay.millis-email}") long delayMillis) {
        this.mailBox = mailBox;
        this.delayEnabled = delayEnabled;
        this.delayMillis = delayMillis;
    }

    @PostMapping(value = "/intake", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> intake(HttpServletRequest request, @RequestBody Map<String, Map<String, Object>> body) throws MessagingException {
        this.delayResponse();

        //Use the language to differentiate in error messages and / or any textual return information
        String language = request.getHeader("Accept-Language");
        //See /test/resources/data/request.json for an example request-body
        Map<String, Object> person = body.get("person");
        Map<String, Object> offering = body.get("offering");

        String offeringId = (String) offering.get("offeringId");
        String mail = (String) person.get("mail");

        LOG.debug("Starting email intake for " + mail);

        mailBox.sendUserConfirmation(
                (String) person.get("displayName"),
                mail,
                offeringId,
                getOfferingName(offering),
                (String) person.get("secondaryMail"),
                (String) person.get("initials"),
                (String) person.get("personId"));

        String message = StringUtils.hasText(language) && language.startsWith("nl") ? "Controlleer je mailbox voor meer informatie" : "Check your mailbox for more information";

        Map<String, Object> result = new HashMap<>();
        result.put("result", "ok");
        result.put("code", 200);
        result.put("message", message);
        result.put("oo-api-offering-id", offeringId);
        //put a redirect if the user needs to be redirected to a different gui

        LOG.debug("Finished email intake for " + mail + ". Sending code 200 back.");

        return ResponseEntity.ok(result);
    }

    @SuppressWarnings("unchecked")
    private String getOfferingName(Map<String, Object> offering) {
        Object name = offering.get("name");
        if (name instanceof String) {
            return (String) name;
        }
        return ((Map<String, String>)((List) offering.get("name")).get(0)).get("value");

    }

    @SneakyThrows
    void delayResponse() {
        if (delayEnabled) {
            Thread.sleep(delayMillis);
        }
    }


}
