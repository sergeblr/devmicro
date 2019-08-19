package com.epam.summer19.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * model test for Cafe Menu.
 */
public class OrderTest {

    private Order order = new Order();

    @Test
    public void getOrderId() {
        order.setOrderId(4);
        Assert.assertTrue(order.getOrderId().equals(4));
    }

    @Test
    public void getOrderEmployeeId() {
        order.setOrderEmployeeId(2);
        Assert.assertTrue(order.getOrderEmployeeId().equals(2));
    }

    @Test
    public void getOrderStatus() {
        order.setOrderStatus(1);
        Assert.assertTrue(order.getOrderStatus().equals(1));
    }
}
