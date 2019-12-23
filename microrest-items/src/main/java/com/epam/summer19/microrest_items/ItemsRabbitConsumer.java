package com.epam.summer19.microrest_items;

import com.epam.summer19.model.Item;
import com.epam.summer19.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

public class ItemsRabbitConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemsRabbitConsumer.class);

    @Autowired
    private ItemService itemService;

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private Queue itemsQueueGetAll;


/*    @RabbitListener(queues = "#{queue.getName()}")
    public List<Item> receivedItems(String param) {
        LOGGER.debug("ItemsRabbitConsumer: Working with param: {}", param);
        if (param.equals("listAllItemsParam")) {
*//*            try{ Thread.sleep(100000); }
            catch (InterruptedException ex) {Thread.currentThread().interrupt();}*//*
            return itemService.findAll();
        }
        else
            return new ArrayList<>();
        *//*else return itemService.findItemById(Integer.parseInt(param));*//*
    }
}*/

    @RabbitListener(queues = "#{itemsQueueGetAll.getName()}")
    public List<Item> receivedItems(String msg) {
        LOGGER.debug("ItemsRabbitConsumer: Working with param: {}", msg);
            return itemService.findAll();
    }
}