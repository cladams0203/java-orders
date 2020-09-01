package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.models.Customer;

import java.util.List;

public interface CustomerServices {

    List<Customer> findAllCustomers();

    Customer findCustomerById(long id);

    List<Customer> findAllCustomersByLikeName(String custname);

    Customer update(Customer customer, long id);


    Customer save(Customer customer);
}
