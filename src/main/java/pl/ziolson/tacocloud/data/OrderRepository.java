package pl.ziolson.tacocloud.data;

import pl.ziolson.tacocloud.Order;

public interface OrderRepository {

    Order save(Order order);
}
