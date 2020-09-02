package com.lambdaschool.crudyorders.services;


import com.lambdaschool.crudyorders.models.Order;
import com.lambdaschool.crudyorders.models.Payment;
import com.lambdaschool.crudyorders.repositories.CustomersRepository;
import com.lambdaschool.crudyorders.repositories.OrdersRepository;
import com.lambdaschool.crudyorders.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service(value = "orderServices")
public class OrderServicesImpl implements OrderServices {

    @Autowired
    OrdersRepository ordersrepos;

    @Autowired
    CustomersRepository custrepos;

    @Autowired
    PaymentRepository paymentrepos;

    @Override
    public Order findOrderById(long orderid) {
        return ordersrepos.findById(orderid)
                .orElseThrow(() -> new EntityNotFoundException("Order " + orderid + " Not Found"));
    }

    @Transactional
    @Override
    public Order save(Order order) {
        Order newOrder = new Order();
        if(order.getOrdnum() != 0){
            ordersrepos.findById(order.getOrdnum())
                    .orElseThrow(() -> new EntityNotFoundException("Order " + order.getOrdnum() + " Not Found"));
            newOrder.setOrdnum(order.getOrdnum());
        }
        newOrder.setOrderdescription(order.getOrderdescription());
        newOrder.setAdvanceamount(order.getAdvanceamount());
        newOrder.setOrdamount(order.getOrdamount());
        //many to one
        newOrder.setCustomer(custrepos.findById(order.getCustomer().getCustcode())
            .orElseThrow(() -> new EntityNotFoundException("Customer " + order.getCustomer().getCustcode() + " Not Found"))
        );
        //many to many
        newOrder.getPayments().clear();
        for (Payment p : order.getPayments()){
            Payment newpay = paymentrepos.findById(p.getPaymentid())
                    .orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + " Not Found"));
            newOrder.getPayments().add(newpay);
        }

        return ordersrepos.save(newOrder);
    }
    @Transactional
    @Override
    public Order update(Order order, long orderid) {
        Order currentOrder = ordersrepos.findById(orderid)
                .orElseThrow(() -> new EntityNotFoundException("Order " + orderid + " Not Found"));
        if(order.getOrderdescription() != null){
            currentOrder.setOrderdescription(order.getOrderdescription());
        }
        if(order.getAdvanceamount() != 0){
            currentOrder.setAdvanceamount(order.getAdvanceamount());
        }
        if(order.getOrdamount() != 0) {
            currentOrder.setOrdamount(order.getOrdamount());
        }
        //many to one
        if(order.getCustomer() != null){
            currentOrder.setCustomer(custrepos.findById(order.getCustomer().getCustcode())
                .orElseThrow(() -> new EntityNotFoundException("Customer " + order.getCustomer().getCustcode() + " Not Found"))
            );
        }
        //many to many
        currentOrder.getPayments().clear();
        for (Payment p : order.getPayments()){
            Payment newpay = paymentrepos.findById(p.getPaymentid())
                    .orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + " Not Found"));
            currentOrder.getPayments().add(newpay);
        }
        return ordersrepos.save(currentOrder);
    }
    @Transactional
    @Override
    public void delete(long id) {
        if(ordersrepos.findById(id).isPresent()){
            ordersrepos.deleteById(id);
        }else{
            throw new EntityNotFoundException("Order " + id + " Not Found");
        }
    }
}
