package mail.api;

import mail.MailBox;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class IntakeEndpoint {

    private final MailBox mailBox;

    public IntakeEndpoint(MailBox mailBox) {
        this.mailBox = mailBox;
    }

    @PostMapping(value = "/intake", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> intake(@RequestBody Map<String, Map<String, Object>> body) {
        //See /test/resources/data/request.json for an example request-body
        Map<String, Object> person = body.get("person");
        Map<String, Object> offering = body.get("offering");

        String offeringId = (String) offering.get("offeringId");
        mailBox.sendUserConfirmation(
                (String) person.get("displayName"),
                (String) person.get("mail"),
                offeringId,
                (String) offering.get("name"));

        Map<String, Object> result = new HashMap<>();
        result.put("result", "ok");
        result.put("code", 200);
        result.put("message", "Check your mailbox for more information");
        result.put("oo-api-offering-id", offeringId);
        //put a redirect if the user needs to be redirected to a different gui
        return ResponseEntity.ok(result);
    }

}
