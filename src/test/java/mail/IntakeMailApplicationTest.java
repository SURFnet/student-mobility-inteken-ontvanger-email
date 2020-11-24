package mail;

import org.junit.jupiter.api.Test;

class IntakeMailApplicationTest {

    @Test
    void main() {
        IntakeMailApplication.main(new String[]{"--server.port=8088"});
    }
}