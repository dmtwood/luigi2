// *********************   STEP (4.b.1.b ) | CREATE SERVICES (IF,) CLASSES & METHODS  *******************************/
// @Services & @Transactional
// inject REPOSITORY-bean(S) in SERVICE CONSTRUCTOR


package be.vdab.luigi.services;
//

import be.vdab.luigi.domain.Pizza;
import be.vdab.luigi.repositories.PizzaRepository;
import be.vdab.luigi.services.PizzaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DefaultPizzaService implements PizzaService {

    private final PizzaRepository pizzaRepository;

    // inject bean that implements PizzaRepository (e.g. JdbcPizzaRepository)
    public DefaultPizzaService(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }


    @Override
    public long create(Pizza pizza) {
        return pizzaRepository.create(pizza);
    }

    @Override
    public void update(Pizza pizza) {
        pizzaRepository.update(pizza);
    }

    @Override
    public void delete(long id) {
        pizzaRepository.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pizza> findAll() {
        return pizzaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Pizza> findById(long id) {
        return pizzaRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pizza> findByPrijsBetween(BigDecimal van, BigDecimal tot) {
        return pizzaRepository.findByPrijsBetween(van, tot);
    }

    @Override
    @Transactional(readOnly = true)
    public long findAantalPizzas() {
        return pizzaRepository.findAantalPizzas();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BigDecimal> findUniekePrijzen() {
        return pizzaRepository.findUniekePrijzen();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pizza> findByPrijs(BigDecimal prijs) {
        return pizzaRepository.findByPrijs(prijs);
    }
}
