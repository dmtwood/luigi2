package be.vdab.luigi.controllers;

import be.vdab.luigi.sessions.Identificatie;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

// central class passing session data | instead of adding vars, consts & altering @..Mapping methods to pass session data to TL-page
// @ControllerAdvice indicates class supplying session data to all TL-pages for all controllers
@ControllerAdvice
class MyControllerAdvice {

    // create a var to holds session data to send to TL-pages
    private  final Identificatie identificatie;


    // inject in with session bean
    public MyControllerAdvice(Identificatie identificatie) {
        this.identificatie = identificatie;
    }


    // @ModelAttribute facilitates a method to pass data to a TL-page
    @ModelAttribute

    // use a Model (from ModelAndView) as param
    void extraDataToevoegenAanModel(Model model) {
        // and use the add.Attribute method from MAV to pass data to TL
        model.addAttribute(identificatie);
    }

}
