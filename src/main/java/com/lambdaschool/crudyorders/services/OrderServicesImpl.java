package com.lambdaschool.crudyorders.services;


import com.lambdaschool.crudyorders.models.Order;
import com.lambdaschool.crudyorders.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service(value = "orderServices")
public class OrderServicesImpl implements OrderServices {

    @Autowired
    OrdersRepository ordersrepos;

    @Override
    public Order save(Order order) {
        return ordersrepos.save(order);
    }

    @Override
    public Order findOrderById(long orderid) {
        return ordersrepos.findById(orderid)
                .orElseThrow(() -> new EntityNotFoundException("Order " + orderid + " Not Found"));
    }
}
