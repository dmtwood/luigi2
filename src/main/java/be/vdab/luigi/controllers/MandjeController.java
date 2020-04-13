// *********************   STEP (4.c.2) | CREATE CONTROLLER CLASSES & METHODS  *******************************/
// inject SERVICE-bean(S) in CONTROLLER-CONSTRUCTOR
// Classes >>  @Controller & @RequestMapping

package be.vdab.luigi.controllers;

import be.vdab.luigi.domain.Pizza;
import be.vdab.luigi.services.PizzaService;
import be.vdab.luigi.sessions.Mandje;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("mandje")
class MandjeController {

    private final Mandje mandje;
    private final PizzaService pizzaService;

    MandjeController(Mandje mandje, PizzaService pizzaService) {
        this.mandje = mandje;
        this.pizzaService = pizzaService;
    }


    @GetMapping
    public ModelAndView toonMandje() {
        List<Pizza> allePizzas = pizzaService.findAll();
        ModelAndView modelAndView = new ModelAndView("mandje")
                .addObject("allePizzas", allePizzas);
        if (mandje.isGevuld()) {
            modelAndView.addObject("pizzasInMandje",
                    allePizzas.stream().filter(pizza -> mandje.bevat(pizza.getId()))
                            .collect(Collectors.toList()));
        }
        return modelAndView;
    }


    @PostMapping
    public String voegToe(long id) {
        mandje.voegToe(id);
        return "redirect:/mandje";
    }
}
