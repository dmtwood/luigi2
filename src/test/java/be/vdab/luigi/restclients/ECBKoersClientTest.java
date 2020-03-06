package be.vdab.luigi.restclients;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ECBKoersClientTest {
    private ECBKoersClient ecbKoersClient;
    @BeforeEach
    void beforeEach(){
        ecbKoersClient = new ECBKoersClient();
    }
    @Test
    void koersIsPos(){
        Assertions.assertThat(ecbKoersClient.getDollarKoers()).isPositive();
    }
}
