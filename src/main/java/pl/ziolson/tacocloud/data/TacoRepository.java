package pl.ziolson.tacocloud.data;

import pl.ziolson.tacocloud.Taco;

public interface TacoRepository {

    Taco save(Taco taco);
}
