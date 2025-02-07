// *********************   STEP (4.b.1.a ) | CREATE SERVICES IF (, CLASSES & METHODS )  *******************************/
package be.vdab.luigi.services;

import be.vdab.luigi.domain.Pizza;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PizzaService {
    long create(Pizza pizza);
    void update(Pizza pizza);
    void delete(long id);
    List<Pizza> findAll();
    Optional<Pizza> findById(long id);
    List<Pizza> findByPrijsBetween(BigDecimal van, BigDecimal tot);
    long findAantalPizzas();
    List<BigDecimal> findUniekePrijzen();
    List<Pizza> findByPrijs(BigDecimal prijs);
}
