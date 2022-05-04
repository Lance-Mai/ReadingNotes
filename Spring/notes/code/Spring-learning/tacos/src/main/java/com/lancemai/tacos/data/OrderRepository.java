package com.lancemai.tacos.data;

import com.lancemai.tacos.Order;

public interface OrderRepository {
    Order save(Order order);
}
