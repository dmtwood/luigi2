package be.vdab.luigi.restclients;

import be.vdab.luigi.exceptions.KoersClientException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Pattern;


@Component // creates a bean to inject in DefaultEuroService
//@Qualifier("Fixer")
// when +1 bean classes can be implemented in the same (DefaultEuro)Service, add a qualifier to choose which one
@Order(1) // replaces Qualifier
class FixerKoersClient implements KoersClient {
    private static final Pattern PATTERN = Pattern.compile(".*\"USD\":");
    private final URL url;

    FixerKoersClient() {
        try {
            url = new URL(
                    "http://data.fixer.io/api/latest?access_key=4f9f3c79f04e6b39fc18a0f8c1f90f9d&symbols=USD");
        } catch (MalformedURLException ex) {
            throw new KoersClientException("Fixer URL is verkeerd.");
        }
    }
    @Override
    public BigDecimal getDollarKoers() {
        try (Scanner scanner = new Scanner(url.openStream())) {
            scanner.skip(PATTERN);
            scanner.useDelimiter("}");
            return new BigDecimal(scanner.next());
        } catch (IOException | NumberFormatException ex) {
            throw new KoersClientException("Kan koers niet lezen via Fixer.", ex);
        }
    }
}
