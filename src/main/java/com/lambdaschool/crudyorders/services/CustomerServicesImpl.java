package com.lambdaschool.crudyorders.services;


import com.lambdaschool.crudyorders.models.Customer;
import com.lambdaschool.crudyorders.repositories.CustomersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "customerServices")
public class CustomerServicesImpl implements CustomerServices {

    @Autowired
    private CustomersRepository custrepos;

    @Override
    public Customer save(Customer customer) {
        return custrepos.save(customer);
    }

    @Transactional
//    @Override
//    public Customer save(Customer customer){
//        return custrepos.save(customer);
//    }



    @Override
    public List<Customer> findAllCustomers() {
        List<Customer> list = new ArrayList<>();
        custrepos.findAll().iterator().forEachRemaining(list::add);
        return list;
    }


}
