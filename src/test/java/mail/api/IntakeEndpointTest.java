package mail.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.internal.util.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@ActiveProfiles(value = "test", inheritProfiles = false)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntakeEndpointTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void before() {
        RestAssured.port = port;
    }

    @Test
    void offerings() throws IOException {
        given()
                .when()
                .contentType(ContentType.JSON)
                .auth().basic("user", "secret")
                .body(IOUtils.toByteArray(new ClassPathResource("data/request.json").getInputStream()))
                .post("/intake")
                .then()
                .statusCode(200)
                .body("result", equalTo("ok"));


    }

}