package com.lambdaschool.crudyorders.controllers;


import com.lambdaschool.crudyorders.models.Order;
import com.lambdaschool.crudyorders.services.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private OrderServices orderServices;

    //http://localhost:2019/orders/order/:id
    @GetMapping(value = "/order/{orderid}", produces = "application/json")
    public ResponseEntity<?> findOrderById(@PathVariable long orderid) {
        Order order = orderServices.findOrderById(orderid);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
    //http://localhost:2019/orders/order
    @PostMapping(value = "/order", consumes = "application/json")
    public ResponseEntity<?> addNewOrder(@Valid @RequestBody Order newOrder){
        newOrder.setOrdnum(0);
        newOrder = orderServices.save(newOrder);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newOrderURI = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/" + newOrder.getOrdnum())
                .build()
                .toUri();
        responseHeaders.setLocation(newOrderURI);
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }
    //http://localhost:2019/orders/order/:id
    @PutMapping(value = "/order/{orderid}", consumes = "application/json")
    public ResponseEntity<?> updateFullOrder(@Valid @RequestBody Order updateOrder, @PathVariable long orderid){
        orderServices.update(updateOrder, orderid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    //http://localhost:2019/orders/order/:id
    @DeleteMapping(value = "/order/{orderid}")
    public ResponseEntity<?> deleteOrder(@PathVariable long orderid){
        orderServices.delete(orderid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
