package com.epam.summer19.microrest_items;

import com.epam.summer19.model.Item;
import com.epam.summer19.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class ItemsRabbitConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemsRabbitConsumer.class);

    @Autowired
    private ItemService itemService;

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private DirectExchange exchange;

    private List<Item> itemsList;
    public List<Item> getItemsList() {
        return itemsList;
    }

    @RabbitListener(queues = "rpc.items.queue")
    public List<Item> receivedItems(String param) {
        LOGGER.debug("ItemsRabbitConsumer: Working with param: {}", param);
        /*itemsList = template.convertSendAndReceive(exchange.getName(), "itemsrpc", List<Item>);*/
        if (param.equals("listAllItemsParam"))
            return itemService.findAll();
        else
            return new ArrayList<>();
        /*else return itemService.findItemById(Integer.parseInt(param));*/
    }
}
