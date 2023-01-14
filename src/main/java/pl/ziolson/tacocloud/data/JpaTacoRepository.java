package pl.ziolson.tacocloud.data;

import org.springframework.data.repository.CrudRepository;
import pl.ziolson.tacocloud.Taco;

public interface JpaTacoRepository extends CrudRepository<Taco, Long> {
}
