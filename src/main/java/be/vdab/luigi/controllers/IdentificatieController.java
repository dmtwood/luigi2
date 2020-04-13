package be.vdab.luigi.controllers;


import be.vdab.luigi.sessions.Identificatie;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("identificatie")
class IdentificatieController {
    private final Identificatie identificatie;

    // inject the session bean
    public IdentificatieController(Identificatie identificatie) {
        this.identificatie = identificatie;
    }

    // pass the email-object to the TL-page to use as a form-object
    // first request >> spring retrieves session id from RAM
    // next >> spring reads the Identificatie-object stored in the session from first request
    public @GetMapping
    ModelAndView identificatie() {
        return new ModelAndView("identificatie", "identificatie", identificatie);
    }

    // handles form-submit that changes session-userdata >> post , not get
    public @PostMapping

    // spring creates Identificatie-object to capture and validate the form-submitted email-Value
    String identificatie(@Valid Identificatie identificatie, Errors errors) {
        if (errors.hasErrors()) {
            return "identificatie";
        }
        // set the submitted email-Value as session-Value for future requests
        this.identificatie.setEmailAdres(identificatie.getEmailAdres());
        return "redirect:/";
    }



}
