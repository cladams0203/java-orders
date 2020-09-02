package com.lambdaschool.crudyorders.services;


import com.lambdaschool.crudyorders.models.Customer;
import com.lambdaschool.crudyorders.models.Order;
import com.lambdaschool.crudyorders.models.Payment;
import com.lambdaschool.crudyorders.repositories.AgentsRepository;
import com.lambdaschool.crudyorders.repositories.CustomersRepository;
import com.lambdaschool.crudyorders.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "customerServices")
public class CustomerServicesImpl implements CustomerServices {

    @Autowired
    private CustomersRepository custrepos;

    @Autowired
    private PaymentRepository paymentrepos;

    @Autowired
    private AgentsRepository agentrepos;


    @Override
    public Customer findCustomerById(long id) {

        return custrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer " + id + " Not Found"));
    }

    @Override
    public List<Customer> findAllCustomers() {
        List<Customer> list = new ArrayList<>();
        custrepos.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public List<Customer> findAllCustomersByLikeName(String custname) {
        return custrepos.findByCustnameContainingIgnoringCase(custname);
    }

    @Transactional
    @Override
    public Customer save(Customer customer) {
        Customer newCustomer = new Customer();
        if(customer.getCustcode() != 0){
            custrepos.findById(customer.getCustcode())
                    .orElseThrow(() -> new EntityNotFoundException("Customer " + customer.getCustcode() + " Not Found"));
            newCustomer.setCustcode(customer.getCustcode());
        }
        newCustomer.setCustname(customer.getCustname());
        newCustomer.setCustcity(customer.getCustcity());
        newCustomer.setWorkingarea(customer.getWorkingarea());
        newCustomer.setCustcountry(customer.getCustcountry());
        newCustomer.setGrade(customer.getGrade());
        newCustomer.setOpeningamt(customer.getOpeningamt());
        newCustomer.setReceiveamt(customer.getReceiveamt());
        newCustomer.setPaymentamt(customer.getPaymentamt());
        newCustomer.setOutstandingamt(customer.getOutstandingamt());
        newCustomer.setPhone(customer.getPhone());
        newCustomer.setAgent(customer.getAgent());

        //many to one
        newCustomer.setAgent(agentrepos.findById(customer.getAgent().getAgentcode())
            .orElseThrow(() -> new EntityNotFoundException("Agent " + customer.getAgent().getAgentcode() + " Not Found"))
        );

        // one to many
        newCustomer.getOrders().clear();
        for (Order o : customer.getOrders()){
            Order newOrder = new Order(o.getOrdamount(),o.getAdvanceamount(), newCustomer, o.getOrderdescription());
            newOrder.getPayments().clear();
            for (Payment p : o.getPayments()){
                Payment newpay = paymentrepos.findById(p.getPaymentid())
                        .orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + " Not Found"));
                newOrder.getPayments().add(newpay);
            }
            newCustomer.getOrders().add(newOrder);
        }

        return custrepos.save(newCustomer);
    }
    @Transactional
    @Override
    public Customer update(Customer customer, long id) {
        Customer currentCustomer = custrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer " + id + " Not Found"));

        if(customer.getCustname() != null){
            currentCustomer.setCustname(customer.getCustname());
        }
        if(customer.getCustcity() != null){
            currentCustomer.setCustcity(customer.getCustcity());
        }
        if(customer.getWorkingarea() != null){
            currentCustomer.setWorkingarea(customer.getWorkingarea());
        }
        if(customer.getCustcountry() != null){
            currentCustomer.setCustcountry(customer.getCustcountry());
        }
        if(customer.getGrade() != null){
            currentCustomer.setGrade(customer.getGrade());
        }
        if(customer.getOpeningamt() != 0){
            currentCustomer.setOpeningamt(customer.getOpeningamt());
        }
        if(customer.getReceiveamt() != 0){
            currentCustomer.setReceiveamt(customer.getReceiveamt());
        }
        if(customer.getPaymentamt() != 0){
            currentCustomer.setPaymentamt(customer.getPaymentamt());
        }
        if (customer.getOutstandingamt() != 0){
            currentCustomer.setOutstandingamt(customer.getOutstandingamt());
        }
        if(customer.getPhone() != null){
            currentCustomer.setPhone(customer.getPhone());
        }
        if(customer.getAgent() != null){
            //many to one
            currentCustomer.setAgent(agentrepos.findById(customer.getAgent().getAgentcode())
                    .orElseThrow(() -> new EntityNotFoundException("Agent " + customer.getAgent().getAgentcode() + " Not Found"))
            );
        }
        // one to many
        if(customer.getOrders().size() > 0) {
            currentCustomer.getOrders().clear();
            for (Order o : customer.getOrders()){
                Order newOrder = new Order(o.getOrdamount(),o.getAdvanceamount(), currentCustomer, o.getOrderdescription());
                newOrder.getPayments().clear();
                for (Payment p : o.getPayments()){
                    Payment newpay = paymentrepos.findById(p.getPaymentid())
                            .orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + " Not Found"));
                    newOrder.getPayments().add(newpay);
                }
                currentCustomer.getOrders().add(newOrder);
            }
        }

        return custrepos.save(currentCustomer);

    }
    @Transactional
    @Override
    public void delete(long id) {
        if(custrepos.findById(id).isPresent()){
            custrepos.deleteById(id);
        }else{
            throw new EntityNotFoundException("Customer " + id + " Not Found");
        }
    }
}
