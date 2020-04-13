package be.vdab.luigi.forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public class AddPizzaForm {
    @NotNull @PositiveOrZero private final String naam;
    @NotNull @PositiveOrZero private final BigDecimal prijs;
    @NotNull @PositiveOrZero private final Boolean pikant;


    public AddPizzaForm(String naam, BigDecimal prijs, Boolean pikant) {
        this.naam = naam;
        this.prijs = prijs;
        this.pikant = pikant;
    }

    public String getNaam() {
        return naam;
    }

    public BigDecimal getPrijs() {
        return prijs;
    }

    public Boolean getPikant() {
        return pikant;
    }
}
