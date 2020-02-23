package be.vdab.luigi.controllers;

import be.vdab.luigi.domain.Pizza;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import java.math.BigDecimal;
import java.util.Arrays;

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
    // verwerkt GET requests naar /pizzas en werkt samen met pizzas.html
    public ModelAndView pizzas(){
                //     html thymeleaf creates , name used to parse the data, private var with pizza strings
        return new ModelAndView("pizzas", "pizzas", pizzas);
    }

    // RequestMapping > pizzas & GetMapping > {id} >> URI template: pizzas/{id}
    @GetMapping("{id}")


    // verwerkt GET requests naar /pizza/1 (, 2, 3, ...) en werkt samen met pizza.html
//    spring stopt de waarde van de path var met naam id in de URL van de request. vb pizzas/1 >> id=1
    public ModelAndView pizza(@PathVariable long id) {

        // create corresponding page "pizza"
        ModelAndView modelAndView2 = new ModelAndView("pizza");

        // if the pizza with this id is found, ad it to the pizza ThymeLeaf (TL) page
        Arrays.stream(pizzas)
                .filter(pizza -> pizza.getId() == id).findFirst()
                .ifPresent(pizza -> modelAndView2.addObject( pizza));
//                .ifPresent(modelAndView2::addObject);
                                    // spring neemt automatisch lowercase ClassName als naam van de TL-data
        return modelAndView2;
    }


}
