package be.vdab.luigi.controllers;

import be.vdab.luigi.domain.Pizza;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;

@Controller
@RequestMapping("pizzas")
    //        (1) pizzas betekent hier: de URL van de requests die de controller verwerkt.

public class PizzaController {

    private final Pizza[] pizzas = {
            new Pizza(1,"Prosciutto", BigDecimal.valueOf(7), true),
            new Pizza(2, "Margherita", BigDecimal.valueOf(5), false),
            new Pizza(3, "Polo", BigDecimal.valueOf(8), true),
            new Pizza(4,"Calzone", BigDecimal.valueOf(6), false),
            new Pizza(5,"Pepperoni", BigDecimal.valueOf(9), true)
    };

    @GetMapping
    public ModelAndView pizzas(){
                //     html thymeleaf creates , name used to parse the data, private var with pizza strings
        return new ModelAndView("pizzas", "pizzas", pizzas);
    }


}
