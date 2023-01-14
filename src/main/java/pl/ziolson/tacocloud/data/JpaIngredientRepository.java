package pl.ziolson.tacocloud.data;

import org.springframework.data.repository.CrudRepository;
import pl.ziolson.tacocloud.Ingredient;

public interface JpaIngredientRepository extends CrudRepository<Ingredient, String> {
}
