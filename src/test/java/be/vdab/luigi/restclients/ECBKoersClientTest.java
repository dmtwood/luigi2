package be.vdab.luigi.restclients;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@Import(ECBKoersClient.class)
@PropertySource("application.properties")
class ECBKoersClientTest {

    private final ECBKoersClient ecbKoersClient;

    ECBKoersClientTest(ECBKoersClient client) {
        this.ecbKoersClient=client;
    }

//    @BeforeEach
//    void beforeEach(){
//        ecbKoersClient = new ECBKoersClient();
//    }

    @Test
    void koersIsPos(){
        assertThat(ecbKoersClient.getDollarKoers()).isPositive();
    }
}