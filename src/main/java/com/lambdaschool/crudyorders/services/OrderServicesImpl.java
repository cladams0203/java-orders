package com.lambdaschool.crudyorders.services;


import com.lambdaschool.crudyorders.models.Order;
import com.lambdaschool.crudyorders.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "orderServices")
public class OrderServicesImpl implements OrderServices {

    @Autowired
    OrdersRepository orderrepos;

    @Override
    public Order save(Order order) {
        return orderrepos.save(order);
    }
}
