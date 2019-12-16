/*
package com.epam.summer19.microrest_items;

import com.epam.summer19.model.Item;
import com.epam.summer19.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin(origins = {"http://localhost:8084", "http://localhost:8085", "http://localhost:8086"}, maxAge = 1800)
@RestController
public class MicroRestItemsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MicroRestItemsController.class);

    @Autowired
    ItemsRabbitConsumer itemsRabbitConsumer;

*/
/*    @GetMapping(value = "/items")
    public Collection<Item> findAll() {
        LOGGER.debug("ItemRestController: findAll()");
        return itemsRabbitConsumer.getItemsList();
            *//*
*/
/*itemService.findAll();*//*
*/
/*
    }*//*


*/
/*
    *//*
*/
/**
     * curl -H "Content-Type: application/json" -X POST -d '{"itemName":"Something","itemPrice":"8.8"}' ##NOTNEEDED: -v http://localhost:8082/item
     *//*
*/
/*
    @PostMapping(value = "/items")              *//*
*/
/** value = "/item" **//*
*/
/*
    public void add(@RequestBody Item item) {
        LOGGER.debug("ItemRestController: add({})", item);
        itemService.add(item);
    }

    @PutMapping(value = "/items")               *//*
*/
/** value = "/item" **//*
*/
/*
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void update(@RequestBody Item item) {
        LOGGER.debug("ItemRestController:update({})", item);
        itemService.update(item);
    }

    @DeleteMapping(value = "/items/{id}")
    public void delete(@PathVariable("id") int id) {
        LOGGER.debug("ItemRestController: delete({})", id);
        itemService.delete(id);
    }


    @GetMapping(value = "/items/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Item findItemById(@PathVariable("id") Integer id) {
        LOGGER.debug("ItemRestController: findItemById({})", id);
        return itemService.findItemById(id);
    }

    @PostMapping(value = "/items/byname")
    public ResponseEntity<Item> postFindItemByName(@RequestBody String itemName) {
        LOGGER.debug("ItemRestController: postFindItemByName({})", itemName);
        // fixme: next IF need to be deleted WHEN ItemRestConsumer postForEntity will be FIXED (String with EXTRA quotes)
        if (itemName.charAt(0) == '"' && itemName.charAt(itemName.length()-1) == '"')
            itemName = itemName.substring(1, itemName.length()-1);
        itemName = itemName.replaceAll("\\\\\"","\"");
        itemName = itemName.replaceAll("\\\\\'","\'");
        Item foundItem = itemService.findItemByName(itemName);
        if(foundItem != null)
            return new ResponseEntity<>(foundItem, HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.OK);
    }*//*

}*/
