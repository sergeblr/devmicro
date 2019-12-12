package com.epam.summer19.microrest_items;

import com.epam.summer19.model.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.util.ArrayList;
import java.util.List;

public class ItemsRabbitConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemsRabbitConsumer.class);

    private List<Item> itemsList;
    public List<Item> getItemsList() {
        return itemsList;
    }

    @RabbitListener(queues = "itemsqueue")
    public void recievedItems(List<Item> items) {
        LOGGER.debug("ItemsRabbitConsumer: receivedItems() = {}", items);
        itemsList = new ArrayList<Item>(items);
    }
}
