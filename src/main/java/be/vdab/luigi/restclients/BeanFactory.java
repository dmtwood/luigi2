package be.vdab.luigi.restclients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URL;

// indicates class configuring beans with Java config
@Configuration
public class BeanFactory {
//    private final URL ecbKoersURL;
//    private final URL fixerKoersURL;
    // call the ...Koers-constructors and pass them  the URL's (from app.props)
//    BeanFactory(
//            @Value("${ecbKoersURL}") URL ecbKoersURL,
//            @Value("${fixerKoersURL}") URL fixerKoersURL) {
//        this.ecbKoersURL = ecbKoersURL;
//        this.fixerKoersURL = fixerKoersURL;
//    }
//    // auto-create a bean based on return value
//    @Bean
//    ECBKoersClient ecbKoersClient(){
//        return new ECBKoersClient(ecbKoersURL);
//    }
//    @Bean
//    FixerKoersClient fixerKoersClient(){
//        return new FixerKoersClient(fixerKoersURL);
//    }
}
