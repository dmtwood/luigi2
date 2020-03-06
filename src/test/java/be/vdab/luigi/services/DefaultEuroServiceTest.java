package be.vdab.luigi.services;

import be.vdab.luigi.restclients.KoersClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DefaultEuroServiceTest {
    @Mock
    private KoersClient koersClient;
    private DefaultEuroService defaultEuroService;

    @BeforeEach
    void beforeEach(){
//        defaultEuroService = new DefaultEuroService(koersClient);

        defaultEuroService = new DefaultEuroService(new KoersClient[] {koersClient});
    }

    @Test
    void naarDollar(){
        when(koersClient.getDollarKoers()).thenReturn(BigDecimal.valueOf(1.5));
        assertThat(defaultEuroService.naarDollar(BigDecimal.valueOf(2)))
                .isEqualByComparingTo("3");
        verify(koersClient).getDollarKoers();
    }


}
