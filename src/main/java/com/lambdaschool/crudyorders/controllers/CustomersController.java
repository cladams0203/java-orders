package com.lambdaschool.crudyorders.controllers;


import com.lambdaschool.crudyorders.models.Customer;
import com.lambdaschool.crudyorders.services.CustomerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomersController {

    @Autowired
    private CustomerServices customerServices;

    //http://localhost:2019/customers/orders
    @GetMapping(value = "/orders", produces = "application/json")
    public ResponseEntity<?> findAllCustomers(){
        List<Customer> custList = customerServices.findAllCustomers();
        return new ResponseEntity<>(custList, HttpStatus.OK);
    }

    //http://localhost:2019/cusatomers/:id
    @GetMapping(value = "/{customerid}", produces = "application/json")
    public ResponseEntity<?> findCustomerById(@PathVariable long customerid){
        Customer customer = customerServices.findCustomerById(customerid);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

}
