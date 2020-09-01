package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.models.Order;

public interface OrderServices  {

    Order save(Order order);

    Order findOrderById(long orderid);
}
