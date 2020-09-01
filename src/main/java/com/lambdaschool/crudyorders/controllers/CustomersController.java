package com.lambdaschool.crudyorders.controllers;


import com.lambdaschool.crudyorders.models.Customer;
import com.lambdaschool.crudyorders.services.CustomerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
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

    //http://localhost:2019/customers/namelike
    @GetMapping(value = "/namelike/{custname}", produces = {"application/json"})
    public ResponseEntity<?> findCustomerByLikeName(@PathVariable String custname){
        List<Customer> custList = customerServices.findAllCustomersByLikeName(custname);
        return new ResponseEntity<>(custList, HttpStatus.OK);
    }
    //http://localhost:2019/customers/customer
    @PostMapping(value = "/customer", consumes = {"application/json"})
    public ResponseEntity<?> addNewCustomer(@Valid @RequestBody Customer newCustomer){
        newCustomer.setCustcode(0);
        newCustomer = customerServices.save(newCustomer);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newCustomerURI = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/" + newCustomer.getCustcode())
                .build()
                .toUri();
        responseHeaders.setLocation(newCustomerURI);
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }
    //http://localhost:2019/customers/customer/:id
    @PutMapping(value = "/customer/{customerid}", consumes = {"application/json"})
    public ResponseEntity<?> updateFullCustomer(@Valid @RequestBody Customer updateCustomer, @PathVariable long customerid){
        customerServices.update(updateCustomer, customerid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
