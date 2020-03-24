package be.vdab.luigi.controllers;

import be.vdab.luigi.domain.Pizza;
import be.vdab.luigi.exceptions.KoersClientException;
import be.vdab.luigi.forms.VanTotPrijsForm;
import be.vdab.luigi.services.EuroService;
import be.vdab.luigi.services.PizzaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("pizzas")
//    controller verwerkt requests naar pizza.html     (1) pizzas betekent hier: de URL van de requests die de controller verwerkt.

public class PizzaController {
    //    private final Pizza[] pizzas = {
//            new Pizza(1,"Prosciutto", BigDecimal.valueOf(7), true),
//            new Pizza(2, "Margherita", BigDecimal.valueOf(5), false),
//            new Pizza(3, "Polo", BigDecimal.valueOf(8), true),
//            new Pizza(4,"Calzone", BigDecimal.valueOf(6), false),
//            new Pizza(5,"Pepperoni", BigDecimal.valueOf(9), true)
//    };
    private final EuroService euroService;
    private final PizzaService pizzaService;

    // factory design pattern LoggerFactory >> getlogger()
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public PizzaController(EuroService euroService, PizzaService pizzaService) {
        this.euroService = euroService;
        this.pizzaService = pizzaService;
    }

    @GetMapping
    // verwerkt GET requests naar /pizzas en werkt samen met pizzas.html
    public ModelAndView pizzas() {
        //     html thymeleaf creates , name used to parse the data, private var with pizza strings
        return new ModelAndView(
                "pizzas",
                "pizzas",
                pizzaService.findAll()); // (pizzas replaced by pizzaService.findAll() on creation PizzaService)
    }

    // RequestMapping > pizzas & GetMapping > {id} >> URI template: pizzas/{id}
    @GetMapping("{id}")
    // verwerkt GET requests naar /pizza/1 (, 2, 3, ...) en werkt samen met pizza.html
    // spring stopt de waarde van de path var met naam id in de URL van de request. vb pizzas/1 >> id=1
    public ModelAndView pizza(@PathVariable long id) {

        // create corresponding page "pizza"
        ModelAndView modelAndView2 = new ModelAndView("pizza");
        // if the pizza with this id is found, ad it to the pizza ThymeLeaf (TL) page
//        Arrays.stream(pizzas)
//                .filter(pizza -> pizza.getId() == id).findFirst()

        pizzaService.findById(id)   // replaces (2+ lines) stream() when PizzaService is implemented

                .ifPresent(pizza -> {
                    modelAndView2.addObject(
                            "pizza",
                            pizza
                    );
                    try {
                        modelAndView2.addObject(
                                "inDollar",
                                euroService.naarDollar(pizza.getPrijs())
                        );
                    } catch (KoersClientException ex) {
                        logger.error("Kan dollar koers niet lezen", ex);
                    }
                });

        // spring neemt automatisch lowercase ClassName als naam van de TL-data
        return modelAndView2;
    }

    private List<BigDecimal> uniekePrijzen() {
        return pizzaService.findAll().stream()
//                Arrays.stream(pizzas)
                .map(pizza -> pizza.getPrijs())
                .distinct().sorted()
                .collect(Collectors.toList());
    }

    @GetMapping("prijzen")
    public ModelAndView prijzen() {
        return new ModelAndView(
                "prijzen",
                "prijzen",
                pizzaService.findUniekePrijzen()
        );
    }

    private List<Pizza> pizzasMetPrijs(BigDecimal prijs) {
        return
//                Arrays.stream(pizzas)
                pizzaService.findAll().stream()
                        .filter(pizza -> pizza.getPrijs().compareTo(prijs) == 0)
                        .collect(Collectors.toList());
    }

    @GetMapping("prijzen/{prijs}")
    public ModelAndView pizzasMetEenPrijs(@PathVariable BigDecimal prijs) {
        return new ModelAndView(
                "prijzen",
                "pizzas",
                pizzasMetPrijs(prijs)
        )
                .addObject(
                        "prijzen",
                        pizzaService.findUniekePrijzen()
                );
        //        ModelAndView modelAndView = new ModelAndView("prijzen","pizzas",pizzasMetPrijs(prijs));
        //        modelAndView.addObject("prijzen", uniekePrijzen());
        //        return modelAndView;
        //      return new ModelAndView("prijzen", "pizzas", pizzasMetPrijs(prijs));
    }


    @GetMapping("vantotprijs/form")
    public ModelAndView vanTotPrijsForm() {
        return new ModelAndView(
                "vantotprijs"
        )
                .addObject(new VanTotPrijsForm(
                                // BigDecimal.ONE, BigDecimal.TEN
                                null, null
                        )
                );
    }


    // method to process the submit request
    @GetMapping("vantotprijs")
    // spring makes object from the param Class VanTotPrijsForm
    public ModelAndView vanTotPrijs(@Valid VanTotPrijsForm form, Errors errors) {
        ModelAndView modelAndView = new ModelAndView("vantotprijs");
        if (errors.hasErrors()) {
            return modelAndView;
        }
        return modelAndView.addObject(
//                "vantotprijs",
                "pizzas",
                pizzaService.findByPrijsBetween(form.getVan(), form.getTot())
        );
    }

    @GetMapping("toevoegen/form")
    public ModelAndView toevoegenform(){
        return new ModelAndView("toevoegen") .addObject( new Pizza(0, "",null, false));
    }


    // method handling POST requests
    @PostMapping
    public String toevoegen(@Valid Pizza pizza, Errors errors, RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) { return "toevoegen";}
                // new ModelAndView("toevoegen");}
        long id = pizzaService.create(pizza);
        redirectAttributes.addAttribute("toegevoegd", id);
        return  "redirect:/pizzas/toegevoegd/{id}";
//        return "redirect:/pizzas";      // spring makes redirect response with /pizzas
        //new ModelAndView("pizzas", "pizzas", pizzaService.findAll());
    }




}
