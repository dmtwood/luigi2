package be.vdab.luigi.restclients;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

//@ExtendWith(SpringExtension.class)
//// make JUnit and Spring collaborate >> spring creates empty IOC-container (inverse of control == dependency injection)
//
//@Import(FixerKoersClient.class)
//// create FixerKoersClient-bean in the IOC >> to use in the unit test
//
//@PropertySource("application.properties")

@ExtendWith(SpringExtension.class)
@Import(FixerKoersClient.class)
@PropertySource("application.properties")

class FixerKoersClientTest {

    //    private FixerKoersClient client; // ~ @BeforeEach
     final FixerKoersClient client;

    // inject the FixerKoersClient-bean in its test-unit-constructor
    FixerKoersClientTest(FixerKoersClient client) {
        this.client = client;
    }

    // with hardcoded URL
//    @BeforeEach
//    void beforeEach() {
//        client = new FixerKoersClient();
//    }

    @Test
    void deKoersMoetPosZijn() {
        assertThat(client.getDollarKoers()).isPositive();
    }
}