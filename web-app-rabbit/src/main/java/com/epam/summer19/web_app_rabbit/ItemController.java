package com.epam.summer19.web_app_rabbit;

import com.epam.summer19.model.Item;
import com.epam.summer19.service.ItemService;
import com.epam.summer19.web_app_rabbit.validators.ItemValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import javax.validation.Valid;
import java.util.List;
import java.util.Queue;


/**
 * Item controller
 */
@Controller
public class ItemController {

    /*RabbitMQ Items props*/
    @Value("${spring.rabbitmq.template.items.exchange}")
    String rabbitmqItemsExchange;

    @Value("${spring.rabbitmq.template.items.routingkey.getallkey}")
    String rabbitmqItemsGetAllKey;

    @Value("${spring.rabbitmq.template.items.routingkey.addkey}")
    String rabbitmqItemsAddKey;

    @Value("${spring.rabbitmq.template.items.routingkey.updatekey}")
    String rabbitmqItemsUpdateKey;

    @Value("${spring.rabbitmq.template.items.routingkey.deletekey}")
    String rabbitmqItemsDeleteKey;

    @Value("${spring.rabbitmq.template.items.routingkey.findbyidkey}")
    String rabbitmqItemsFindByIdKey;

    @Value("${spring.rabbitmq.template.items.routingkey.findbynamekey}")
    String rabbitmqItemsFindByNameKey;

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private ItemService itemService;

    @Autowired
    ItemValidator itemValidator;

    /* RabbitMQ wiring exchange: */
    @Autowired
    private RabbitTemplate template;

    @Autowired
    private DirectExchange itemsExchange;

    @GetMapping(value = "/items")
    public final String listAllItems(Model model) {
        LOGGER.debug("ItemController: listAllItems({})", model);
        /*RabbitMQ send MSG and wait result*/
        List<Item> items = (List<Item>) template.convertSendAndReceive(
                itemsExchange.getName(), rabbitmqItemsGetAllKey, "msg");
        /*itemService.findAll();*/
        model.addAttribute("items", items);
        return "items";
    }

    @GetMapping(value = "/item")
    public final String gotoAddItemPage(Model model) {
        LOGGER.debug("ItemController: gotoAddItemPage({})", model);
        Item item = new Item();
        model.addAttribute("isNew", true);
        model.addAttribute("item", item);
        return "item";
    }

    @PostMapping(value = "/item")
    public String addItem(@Valid Item item, BindingResult result) {
        LOGGER.debug("ItemController: addItem({}, {})", item, result);
        itemValidator.validate(item, result);
        if (result.hasErrors()) {
            return "item";
        } else {
            /*RabbitMQ send ONE item MSG*/
            template.convertSendAndReceive(
                    itemsExchange.getName(), rabbitmqItemsAddKey, item);
            /*itemService.add(item);*/
            return "redirect:/items";
        }
    }

    @GetMapping(value = "/item/{id}")
    public final String gotoEditItemPage(@PathVariable Integer id, Model model) {
        LOGGER.debug("ItemController: gotoEditItemPage({},{})", id, model);
        /*RabbitMQ find ITEM by ID MSG*/
        Item item = (Item) template.convertSendAndReceive(
                itemsExchange.getName(), rabbitmqItemsFindByIdKey, id);
        /*Item item = itemService.findItemById(id);*/
        model.addAttribute("isNew", false);
        model.addAttribute("item", item);
        return "item";
    }

    @PostMapping(value = "/item/{id}")
    public String updateItem(@Valid Item item, BindingResult result) {
        LOGGER.debug("ItemController: updateItem({})", item);
        itemValidator.validate(item, result);
        if (result.hasErrors()) {
            return "item";
        } else {
            /*RabbitMQ update item MSG*/
            template.convertSendAndReceive(
                    itemsExchange.getName(), rabbitmqItemsUpdateKey, item);
            /*itemService.update(item);*/
            return "redirect:/items";
        }

    }

    @GetMapping(value = "/items/{id}/delete")
    public final String deleteItem(@PathVariable Integer id, Model model) {
        LOGGER.debug("ItemController: deleteItem({},{})", id, model);
        /*RabbitMQ delete item by id MSG*/
        template.convertSendAndReceive(
                itemsExchange.getName(), rabbitmqItemsDeleteKey, id);
        /*itemService.delete(id);*/
        return "redirect:/items";
    }
}
