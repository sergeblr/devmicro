package com.epam.summer19.service;

import com.epam.summer19.dao.OrderDao;
import com.epam.summer19.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class OrderServiceImplMockTest {

    @Mock
    private OrderDao mockOrderdao;

    private OrderServiceImpl orderServiceImplUnderTest;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        orderServiceImplUnderTest = new OrderServiceImpl(mockOrderdao);
    }

    @Test
    public void testAddSingle() {
        final Order order = null;
        when(mockOrderdao.add(null)).thenReturn(null);

        orderServiceImplUnderTest.add(order);

        verify(mockOrderdao).add(order);
    }

    @Test
    public void testAddMultiple() {
        final Order orderone = null;
        final Order ordertwo = null;
        when(mockOrderdao.add(null)).thenReturn(null);

        orderServiceImplUnderTest.add(orderone, ordertwo);
    }

    @Test
    public void testUpdate() {
        final Order order = null;

        orderServiceImplUnderTest.update(order);

        verify(mockOrderdao).update(null);
    }

    @Test
    public void testDelete() {
        final Integer orderId = 1;

        orderServiceImplUnderTest.delete(orderId);

        verify(mockOrderdao).delete(1);
    }

    @Test
    public void testFindAll() {
        final List<Order> expectedResult = Arrays.asList();
        when(mockOrderdao.findAll()).thenReturn(Arrays.asList());

        final List<Order> result = orderServiceImplUnderTest.findAll();

        assertEquals(expectedResult, result);
    }

    @Test
    public void testFindOrderById() {
        final Integer orderId = 1;
        final Order expectedResult = new Order();
        expectedResult.setOrderEmployeeId(22);
        when(mockOrderdao.findOrderById(1)).thenReturn(Optional.of(expectedResult));

        final Order result = orderServiceImplUnderTest.findOrderById(orderId);

        assertEquals(expectedResult, result);
    }

    @Test
    public void testFindOrdersByDateTime() {
                final LocalDateTime startDateTime = LocalDateTime.of(2019, 8, 21, 9, 0, 0);
        final LocalDateTime endDateTime = LocalDateTime.of(2019, 8, 21, 9, 0, 0);
        final List<Order> expectedResult = Arrays.asList();
        when(mockOrderdao.findOrdersByDateTime(LocalDateTime.of(2019, 8, 21, 9, 0, 0), LocalDateTime.of(2017, 1, 1, 0, 0, 0))).thenReturn(Arrays.asList());

        final List<Order> result = orderServiceImplUnderTest.findOrdersByDateTime(startDateTime, endDateTime);

        assertEquals(expectedResult, result);
    }
}
