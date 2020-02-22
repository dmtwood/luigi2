package be.vdab.luigi.controllers;

import be.vdab.luigi.domain.Adres;
import be.vdab.luigi.domain.Persoon;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalTime;


// Je vervangt @RestController doort @Controller als een controller samenwerkt met een Thymeleaf pagina.
@Controller

    // @RequestMapping associeert de controller met een URL in je website.
    // In deze: "  /  "  >> welkom pagina
    // De controller verwerkt straks alle browser requests naar die URL.
@RequestMapping("/")


    // Je mag de naam van een controller class vrij kiezen.
    // De class niet public: Je geeft classes, en vars, niet meer visibility dan nodig.
class IndexController {

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
    // @GetMapping >> bijbehorende method (index) verwerkt GET requests. Bij een GET request naar de URL / , roept Spring de method index op.
    @GetMapping

    // Een method die data doorgeeft aan de Thymeleaf pagina heeft als returntype ModelAndView. Model staat voor de data, View staat voor de Thymeleaf pagina.
public ModelAndView index(){

        // (1) Je maakt een ModelAndView object. Het werkt samen met de Thymeleaf pagina index.html.
        ModelAndView modelAndView = new ModelAndView(

                // (2) Je geeft de return waarde van boodschap() door onder de naam boodschap.
                "index", "boodschap", boodschap()
        );

        // (3) Je geeft met de method addObject nog EXTRA data door onder de naam zaakvoerder.  In deze een Persoon object
        modelAndView.addObject("zaakvoerder", new Persoon(
                "Luigi", "Peperone", "7", true, LocalDate.of(1976,8,5), new Adres(
                        "Grote Markt", "69", 2300, "Turnhout")));
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























