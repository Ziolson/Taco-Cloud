package pl.ziolson.tacocloud.data;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pl.ziolson.tacocloud.Ingredient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class JdbcIngredientRepository implements IngredientRepository{

    private JdbcTemplate jdbcTemplate;

    private static final String SELECT_FIND_BY_ID = "SELECT ID, NAME, TYPE FROM INGREDIENT WHERE ID = ?";

    @Override
    public Iterable<Ingredient> findAll() {
        return jdbcTemplate.query("SELECT ID, NAME, TYPE FROM INGREDIENT", this::mapRowToIngredient);
    }

    /**
     * This implementation from Spring in Action 5th use queryForObject method.
     * The queryForObject method return always one object as result; throws an exception if 0 or more than 1 element found.
     */
    @Override
    public Ingredient findById(String id) {
        return jdbcTemplate.queryForObject(SELECT_FIND_BY_ID, this::mapRowToIngredient, id);
    }
    /**
     * Implementation from Spring in Action 6th use query method. The query method return a list of objects.
     * We don't have covered case when results have more than 1 object inside. In that case always first element is returned.
     */
    public Optional<Ingredient> findByIdWithOptional(String id) {
        List<Ingredient> results = jdbcTemplate.query(SELECT_FIND_BY_ID, this::mapRowToIngredient, id);
        return results.size() == 0 ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        jdbcTemplate.update("INSERT INTO INGREDIENT(ID, NAME, TYPE) values (?, ?, ?)",
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getType().toString()
                );
        return ingredient;
    }

    private Ingredient mapRowToIngredient(ResultSet row, int rowNum) throws SQLException {
        return new Ingredient(
                row.getString("id"),
                row.getString("name"),
                Ingredient.Type.valueOf(row.getString("type"))
        );
    }
}
