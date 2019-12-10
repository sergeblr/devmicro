package com.epam.summer19.webappvdn8.consumers;

import com.epam.summer19.model.Item;
import com.epam.summer19.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * Item Consumer (for REST)
 */

public class ItemRestConsumer implements ItemService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemRestConsumer.class);

    private String url;

    private RestTemplate restTemplate;

    /**
     * ItemRestConsumer constructor.
     * @param url
     * @param restTemplate
     */
    public ItemRestConsumer(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    /**
     * findAll() - get all items
     * @return
     */
   /** @Override
    public List<Item> findAll() {
        LOGGER.debug("ItemRestConsumer: findAll()");
        //List<Item> items = restTemplate.getForEntity(url, Item.class).getBody();
        ParameterizedTypeReference<List<Item>> typeRef = new ParameterizedTypeReference<List<Item>>() {};
        List<Item> items = restTemplate.getForEntity(url, typeRef);
        return items;
    }**/
   public List<Item> findAll() {
       LOGGER.debug("ItemRestConsumer: findAll()");
       Item[] items = restTemplate.getForEntity(url, Item[].class).getBody();
       return Arrays.asList(items);
   }

    /**
     * add() new item.
     * @param
     */
    @Override
    public void add(Item item) {
        LOGGER.debug("ItemRestConsumer: add({})", item);
        restTemplate.postForEntity(url, item, Item.class);
    }

    /**
     * update() item
     * @param
     */
    @Override
    public void update(Item item) {
        LOGGER.debug("ItemRestConsumer: update({})", item);
        restTemplate.put(url, item);

    }

    /**
     * delete() item
     * @param
     */
    @Override
    public void delete(Integer itemId) {
        LOGGER.debug("ItemRestConsumer: delete({})", itemId);
        restTemplate.delete(url + "/" + itemId);
    }

    /**
     * findItemById() item
     * @param itemId
     * @return
     */
    @Override
    public Item findItemById(Integer itemId) {
        LOGGER.debug("ItemRestConsumer: findItemById({})", itemId);
        return restTemplate.getForEntity(url + "/" + itemId, Item.class).getBody();
    }

    @Override
    public Item findItemByName(String itemName) {
        LOGGER.debug("ItemRestConsumer: findItemByName({})", itemName);
        // fixme: itemName to REST sending with extra quotes -> ????
        return restTemplate.postForEntity(url + "/byname", itemName, Item.class).getBody();
    }
}