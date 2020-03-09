package be.vdab.luigi.repositories;

import be.vdab.luigi.domain.Pizza;
import be.vdab.luigi.exceptions.PizzaNietGevondenException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.math.BigDecimal;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@JdbcTest
@Import(JdbcPizzaRepository.class)
@Sql("/insertPizzas.sql")
class JDBCPizzaRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    private static final String PIZZAS = "pizzas";
    private final JdbcPizzaRepository repository;

    JDBCPizzaRepositoryTest(JdbcPizzaRepository repository) {
        this.repository = repository;
    }

    @Test
    void findAantal() {
        assertThat(repository.
                findAantalPizzas()
        ).isEqualTo(super.countRowsInTable(PIZZAS));
    }

    @Test
    void findAllgeeftAllePizzasOpIdGesorteerd() {
        assertThat(repository.
                findAll()
        ).hasSize(super.countRowsInTable(PIZZAS));
    }

    @Test
    void create() {
        long id = repository.create(new Pizza(0, "test2", BigDecimal.TEN, false));
        assertThat(id).isPositive();
        assertThat(super
                .countRowsInTableWhere(PIZZAS, "id=" + id)
        ).isZero();
    }

    private long idVanTestPizza() {
        return super.jdbcTemplate.queryForObject(" select id from pizzas where naam='test' ", long.class);
    }

    @Test
    void delete() {
        long id = idVanTestPizza();
        repository.delete(id);
        assertThat(super
                .countRowsInTableWhere(PIZZAS, "id=" + id)
        ).isZero();
    }

    @Test
    void findById() {
        assertThat(repository
                .findById(idVanTestPizza())
                .get()
                .getNaam()
        ).isEqualTo("test ");
    }

    @Test
    void findByWrongIdReturnNoPizza() {
        assertThat(repository.
                findById(-1)
        ).isEmpty();
    }

    @Test
    void update() {
        long id = idVanTestPizza();
        Pizza pizza = new Pizza(id, "test", BigDecimal.ONE, false);
        repository.update(pizza);
        assertThat(super.
                jdbcTemplate.
                queryForObject("select prijs from pizzas where id=?", BigDecimal.class, id)
        ).isOne();
    }

    @Test
    void updateOnbestaandePizzaGeeftEenFout() {
        assertThatExceptionOfType(PizzaNietGevondenException.class).isThrownBy(
                () -> repository.update(new Pizza(-1, "test", BigDecimal.ONE, false)));
    }

    @Test
    void findByPrijsBetween() {
        assertThat(repository.findByPrijsBetween(BigDecimal.ONE, BigDecimal.TEN))
                .hasSize(super.countRowsInTableWhere(PIZZAS, "prijs between 1 and 10"))
                .allSatisfy(pizza ->
                        assertThat(pizza.getPrijs()).isBetween(BigDecimal.ONE, BigDecimal.TEN))
                .extracting(pizza -> pizza.getPrijs()).isSorted();
    }

    @Test
    void findUniekePrijzenGeeftPrijzenOplopend() {
        assertThat(repository.findUniekePrijzen())
                .hasSize(super.jdbcTemplate.queryForObject(
                        "select count(distinct prijs) from pizzas", Integer.class))
                .doesNotHaveDuplicates()
                .isSorted();
    }

    @Test
    void findByPrijs() {
        assertThat(repository.findByPrijs(BigDecimal.TEN))
                .hasSize(super.countRowsInTableWhere(PIZZAS, "prijs=10"))
                .extracting(pizza -> pizza.getNaam().toLowerCase()).isSorted();
    }

    @Test
    void findByIds() {
        long id = idVanTestPizza();
        assertThat(repository.findByIds(Collections.singleton(id)))
                .extracting(pizza -> pizza.getId()).containsOnly(id);
    }

    @Test
    void findByIdsGeeftLegeVerzamelingPizzasBijLegeVerzamelingIds() {
        assertThat(repository.findByIds(Collections.emptySet())).isEmpty();
    }

    @Test
    void findByIdsGeeftLegeVerzamelingPizzasBijOnbestaandeIds() {
        assertThat(repository.findByIds(Collections.singleton(-1L))).isEmpty();

    }

}
