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

    @Value("${spring.rabbitmq.template.items.errormsg}")
    String rabbitmqItemsErrorMsg;

    @Value("${spring.rabbitmq.template.items.successfulmsg}")
    String rabbitmqItemsSuccessfulMsg;


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
    public List<Item> itemsGetAll(String msg) {
        LOGGER.debug("ItemsRabbitConsumer: Working with param: {}", msg);
        try {
            List<Item> items = itemService.findAll();
            LOGGER.debug("ItemsRabbitConsumer: Successfully");
            return items;
        }
        catch (Exception ex)
        {
            LOGGER.error(ex.toString());
            return new ArrayList<>();
        }
    }

    @RabbitListener(queues = "#{itemsQueueAdd.getName()}")
    public String itemsAdd(Item item) {
        LOGGER.debug("ItemsRabbitConsumer: Add item: {}", item);
        try {
            itemService.add(item);
            LOGGER.debug("ItemsRabbitConsumer: Successfully");
            return rabbitmqItemsSuccessfulMsg;
        }
        catch (Exception ex)
        {
            LOGGER.error(ex.toString());
            return rabbitmqItemsErrorMsg;
        }
    }

    @RabbitListener(queues = "#{itemsQueueUpdate.getName()}")
    public String itemsUpdate(Item item) {
        LOGGER.debug("ItemsRabbitConsumer: Update item: {}", item);
        try {
            itemService.update(item);
            LOGGER.debug("ItemsRabbitConsumer: Successfully");
            return rabbitmqItemsSuccessfulMsg;
        }
        catch (Exception ex)
        {
            LOGGER.error(ex.toString());
            return rabbitmqItemsErrorMsg;
        }
    }

    @RabbitListener(queues = "#{itemsQueueDelete.getName()}")
    public String itemsDelete(Integer id) {
        LOGGER.debug("ItemsRabbitConsumer: Delete item by id: {}", id);
        try {
            itemService.delete(id);
            LOGGER.debug("ItemsRabbitConsumer: Successfully");
            return rabbitmqItemsSuccessfulMsg;
        }
        catch (Exception ex)
        {
            LOGGER.error(ex.toString());
            return rabbitmqItemsErrorMsg;
        }
    }

    @RabbitListener(queues = "#{itemsQueueFindByName.getName()}")
    public Item itemsFindByName(String itemName) {
        LOGGER.debug("ItemsRabbitConsumer: Find by name: {}", itemName);
        Item foundItem = itemService.findItemByName(itemName);
        if(foundItem != null)
            return foundItem;
        else
            {
                Item nullItem = new Item();
                nullItem.setItemId(-1);
                return  nullItem;
            }
    }

    @RabbitListener(queues = "#{itemsQueueFindById.getName()}")
    public Item itemsFindById(Integer itemId) {
        LOGGER.debug("ItemsRabbitConsumer: Find by Id (EDIT): {}", itemId);
        try {
            Item updatableItem = itemService.findItemById(itemId);
            LOGGER.debug("ItemsRabbitConsumer: Successfully");
            return updatableItem;
        }
        catch (Exception ex)
        {
            LOGGER.error(ex.toString());
            return new Item();
        }
    }

}