package be.vdab.luigi.repositories;
import be.vdab.luigi.domain.Pizza;
import be.vdab.luigi.exceptions.PizzaNietGevondenException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;

@Repository // creates singleton bean
class JdbcPizzaRepository implements PizzaRepository {

    // object to insert a record in the DB
    private final SimpleJdbcInsert insert;

    // JDBC template bean (which had a dependency on DataSource) to use connection to DB
    private final JdbcTemplate template;

    // take connection bean - create inserting object with it - link to DB table - linkt to PK if it existt
    JdbcPizzaRepository(JdbcTemplate template) {

        // inject JBCD-bean in JDBCPizzaRepository objects
        this.template = template;

        // inject the template (connection bean) in record-inserting object
        this.insert = new SimpleJdbcInsert(template);

        // define the DB-table name to insert in
        insert.withTableName("pizzas");

        // capture auto-generating PK column (when existing)
        insert.usingGeneratedKeyColumns("id");
    }


    @Override
    public long findAantalPizzas() {
        // 2e param Long.class >> gewenste return waarde van queryForObject()
        return template.queryForObject("select count(*) from pizzas", Long.class);
    }

    @Override
    public void delete(long id) {
        // waarde van 2e param, 3e,... >> waarde van , id , ... , ...
        template.update("delete from pizzas where id=?", id);
    }

    @Override
    public void update(Pizza pizza) {
        String sql = "update pizzas set naam=?, prijs=?, pikant=? where id=?";

          //        .update() method returns amount of updates
        if (template.update(
                sql, pizza.getNaam(), pizza.getPrijs(), pizza.isPikant(), pizza.getId()

                // if the amount of updates is 0
                ) == 0) {
                    throw new PizzaNietGevondenException();
                }
    }

    @Override
    public long create(Pizza pizza) {

        // elke entry heeft een kolomnaam als key en een kolomwaarde als value
        Map<String, Object> kolomWaarden = new HashMap<>();

        kolomWaarden.put("naam", pizza.getNaam());
        kolomWaarden.put("prijs", pizza.getPrijs());
        kolomWaarden.put("pikant", pizza.isPikant());

        // .executeAndReturnKey makes and executes an SQL statement to insert a Pizza with kolomwaarden
        Number id = insert.executeAndReturnKey(kolomWaarden);
        return id.longValue();
    }

    // RowMapper converts one row of the resultSet to one <object>
    private final RowMapper<BigDecimal> prijsMapper =
            // RowMapper takes 2 parameters: the resultSet and the row number
            (result, rowNum) ->
                    // and returns the value of the column prijs in the resultSets
                    result.getBigDecimal("prijs");

    private final RowMapper<Pizza> pizzaMapper =
            (result, rowNum) ->
                    // returns a Pizza with column values in the resultSet
                    new Pizza(
                    result.getLong("id"),
                    result.getString("naam"),
                    result.getBigDecimal("prijs"),
                    result.getBoolean("pikant")
            );

    //    De method query voert volgende stappen uit:
    //    a. een lege List<Pizza> maken.
    //    b. een Connection vragen aan de DataSource bean.
    //    c. een Statement met het select statement maken en uitvoeren.
    //    d. itereren over de ResultSet van het resultaat van het Statement.
    //    e. per rij de lambda van pizzaMapper uitvoeren.
    //    f. de Pizza, die je lambda teruggeeft, toevoegen aan de List.
    //    g. de ResultSet, het Statement en de Connection sluiten.
    //    h. de List<Pizza> teruggeven.
    @Override
    public List<Pizza> findAll() {
        String sql = "select id, naam, prijs, pikant from pizzas order by id";
        return template.query(sql, pizzaMapper);
    }

    //    query method heeft ook een versie waarbij je een SQL statement met parameters uitvoert:
    //    De 1° parameter is een select statement. De 2° parameter is een RowMapper. De volgende
    //    parameters zijn waarden voor de parameters in het select statement (aangegeven met ?).
    @Override
    public List<Pizza> findByPrijsBetween(BigDecimal van, BigDecimal tot) {
        String sql = "select id, naam, prijs, pikant from pizzas"
                + " where prijs between ? and ? order by prijs";;
        return template.query(sql, pizzaMapper, van, tot);
    }

    @Override
    public Optional<Pizza> findById(long id) {
        try {
            String sql = "select id, naam, prijs, pikant from pizzas where id=?";
            return Optional.of(template.queryForObject(sql, pizzaMapper, id));
        } catch (IncorrectResultSizeDataAccessException ex) {
            // De method queryForObject vond geen record. Je geeft dan een “lege” Optional terug.
            return Optional.empty();
        }
    }

    @Override
    public List<BigDecimal> findUniekePrijzen() {
        String sql = "select distinct prijs from pizzas order by prijs";
        return template.query(sql, prijsMapper);
    }

    @Override
    public List<Pizza> findByPrijs(BigDecimal prijs) {
        String sql = "select id, naam, prijs, pikant from pizzas"
                + " where prijs=? order by naam";
        return template.query(sql, pizzaMapper, prijs);
    }


    @Override
    public List<Pizza> findByIds(Set<Long> ids) {

        // Als de verzameling ids’s, waarvan je de bijbehorende pizza’s zoekt, leeg is
        if (ids.isEmpty()) {

            // spreek je de database niet aan. Je geeft een lege verzameling pizza’s terug.
            return Collections.emptyList();
        }
        // begin van een SQL statement dat pizza’s zoekt waarvan enkele id’s gekend zijn
        String sql = "select id, naam, prijs, pikant from pizzas where id in (";

        // stop het start SQL statement in een stringbuilder
        StringBuilder builder = new StringBuilder(sql);

        // Je voegt per op te zoeken id een ? en een , toe aan het SQL statement in de stringbuilder
        ids.forEach(id -> builder.append("?,"));

        // Je vervangt de laatste , (die er te veel staat) door een )
        builder.setCharAt(builder.length() - 1, ')');

        // vervolledig het SQL-statement in de stringbuilder
        builder.append(" order by id");

        //  Je roept een versie van de query method op waarbij je als 1° parameter
        //  een SQL statement met meerdere parameters meegeeft.
        //  Je geeft als 2° parameter een array mee met waarden die bij die parameters horen.
        return template.query(builder.toString(), ids.toArray(), pizzaMapper);
    }

}
