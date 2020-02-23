package be.vdab.luigi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;

@Controller
@RequestMapping("os")
// OScController verwerkt requests naar URL ../os
public class OSController {

    private static final String[] OSS = { "Windows", "Macintosh", "Android", "Linux"};

    @GetMapping
    // spring plaatst de inhoud van de RequestHeader User-Agent als parameter

    public ModelAndView os(@RequestHeader("User-Agent") String userAgent) {

        // creëer een gekoppelde pagina os.html
        ModelAndView modelAndView = new ModelAndView("os");

        // zoek op gelijkenissen met strings uit OSS
        Arrays.stream(OSS).filter(os -> userAgent.contains(os))

                // geef de gevonden gelijkenis onder de attributeName "os" door aan de TL-page os.html
                .findFirst().ifPresent(os -> modelAndView.addObject("os", os));

        return modelAndView;
    }


}
