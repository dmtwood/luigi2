package be.vdab.luigi.controllers;

import be.vdab.luigi.domain.Adres;
import be.vdab.luigi.domain.Persoon;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicInteger;


// Je vervangt @RestController doort @Controller als een controller samenwerkt met een Thymeleaf pagina.
@Controller

    // @RequestMapping associeert de controller met een URL in je website.
    // In deze: "  /  "  >> welkom pagina
    // De controller verwerkt straks alle browser requests naar die URL.
@RequestMapping("/")


    // Je mag de naam van een controller class vrij kiezen.
    // De class niet public: Je geeft classes, en vars, niet meer visibility dan nodig.
class IndexController {

    private final AtomicInteger aantalKerenBekeken = new AtomicInteger();

    private String boodschap() {
        int uur = LocalTime.now().getHour();
        if (uur < 12) {
            return "morgen";
        }
        if (uur < 18) {
            return "middag";
        }
        return "avond";
    }


    @GetMapping
public ModelAndView index(@CookieValue(name = "reedsBezocht", required = false)
                                      String reedsBezocht, HttpServletResponse response) {

        ModelAndView modelAndView = new ModelAndView(
                "index", "boodschap", boodschap()
        );

        modelAndView.addObject("zaakvoerder", new Persoon(
                "Luigi", "Peperone", "7", true, LocalDate.of(1976,8,5), new Adres(
                "Grote Markt", "69", 2300, "Turnhout")));

            //               (1) incrementAndGet verhoogt de teller in de AtomicInteger op een thread-safe manier.
            //                   De method geeft daarna de verhoogde teller terug als return waarde.
                modelAndView.addObject("aantalKerenBekeken", aantalKerenBekeken.incrementAndGet());


        Cookie cookie = new Cookie("reedsBezocht", "ja");
        cookie.setMaxAge(31_536_000);
        cookie.setPath("/");
        response.addCookie(cookie);
        if (reedsBezocht != null) {
            modelAndView.addObject("reedsBezocht", true);
        }
        return modelAndView;
    }



//        // De method moet een String terug geven, method-naamkeuze is vrij
//    public String index() {
//        return "index";
//    }

            // Je maakt een StringBuffer of builder die HTML bevat
//        StringBuilder buffer =
//                new StringBuilder("<!doctype html><html><title>Hallo</title><body>");
//        int uur = LocalTime.now().getHour();
//
//            // Je plaatst in de HTML, naargelang het moment van de dag, de tekst Goede morgen, Goede middag of Goede avond.
//        if (uur < 12) {
//            buffer.append("Goede morgen");
//        } else if (uur < 18) {
//            buffer.append("Goede middag");
//        } else {
//            buffer.append("Goede avond");
//        }
//        buffer.append("</body></html>");

            // Spring gebruikt de String die je teruggeeft als response naar de browser.
        // return buffer.toString();

            // met Thymeleaf geef je de pagina terug die HTML naar de browser stuurt.
            // Je moet de extensie (.html) niet vermelden.

}























