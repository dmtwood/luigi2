package be.vdab.luigi.services;

import be.vdab.luigi.exceptions.KoersClientException;
import be.vdab.luigi.restclients.KoersClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public
//@Qualifier("Fixer")
class DefaultEuroService implements EuroService {

    private final KoersClient[] koersClients;

    // @Qualifier to choose which bean is injected (use when +1 client-beans available) >> not when @Order used
    public DefaultEuroService( KoersClient[] koersClients) {
        this.koersClients=koersClients;
    }


    @Override
    public BigDecimal naarDollar(BigDecimal euro) {
        Exception laatsteException = null;
        for (KoersClient koersClient : koersClients) {
            try {
                return euro.multiply(koersClient.getDollarKoers()
                        .setScale(2, RoundingMode.HALF_UP));
            } catch (KoersClientException ex) {
                laatsteException = ex;
            }
        }
        throw new KoersClientException("Kan nergens een dollar koers lezen.", laatsteException);
    }
}
