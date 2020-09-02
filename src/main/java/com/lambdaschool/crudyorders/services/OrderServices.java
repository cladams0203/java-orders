package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.models.Order;

public interface OrderServices  {

    Order save(Order order);

    Order findOrderById(long orderid);

    Order update(Order order, long orderid);

    void delete(long id);
}
