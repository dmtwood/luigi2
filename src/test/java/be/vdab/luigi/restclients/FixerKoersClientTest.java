package be.vdab.luigi.restclients;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
// enkele andere imports
class FixerKoersClientTest {
    private FixerKoersClient client;
    @BeforeEach
    void beforeEach() {
        client = new FixerKoersClient();
    }
    @Test
    void deKoersMoetPositiefZijn() {
        assertThat(client.getDollarKoers()).isPositive();
    }
}