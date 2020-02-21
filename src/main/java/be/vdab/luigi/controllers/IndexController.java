package be.vdab.luigi.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;

    // enkele imports.

    // @RestController kondigt een Controller-Class aan
@RestController

    // @RequestMapping associeert de controller met een URL in je website.
    // In deze: "  /  "  >> welkom pagina
    // De controller verwerkt straks alle browser requests naar die URL.
@RequestMapping("/")


    // Je mag de naam van een controller class vrij kiezen.
    // De class niet public: Je geeft classes, en vars, niet meer visibility dan nodig.
class IndexController {


        // @GetMapping >> bijbehorende method (index) verwerkt GET requests
        // Bij een GET request naar de URL / , roept Spring de method index op.
    @GetMapping

        // De method moet een String terug geven, method-naamkeuze is vrij
    public String index() {

            // Je maakt een StringBuffer of builder die HTML bevat
        StringBuilder buffer =
                new StringBuilder("<!doctype html><html><title>Hallo</title><body>");
        int uur = LocalTime.now().getHour();

            // Je plaatst in de HTML, naargelang het moment van de dag, de tekst Goede morgen, Goede middag of Goede avond.
        if (uur < 12) {
            buffer.append("Goede morgen");
        } else if (uur < 18) {
            buffer.append("Goede middag");
        } else {
            buffer.append("Goede avond");
        }
        buffer.append("</body></html>");

            // Spring gebruikt de String die je teruggeeft als response naar de browser.
        return buffer.toString();
    }
}