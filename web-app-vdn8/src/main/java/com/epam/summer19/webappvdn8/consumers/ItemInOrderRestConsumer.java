package com.epam.summer19.webappvdn8.consumers;

import com.epam.summer19.model.ItemInOrder;
import com.epam.summer19.service.ItemInOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * ItemInOrder Consumer (for REST)
 */

public class ItemInOrderRestConsumer implements ItemInOrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemInOrderRestConsumer.class);

    private String url;

    private RestTemplate restTemplate;

    /**
     * ItemInOrderRestConsumer constructor.
     * @param url
     * @param restTemplate
     */
    public ItemInOrderRestConsumer(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    /**
     * findAll() - get all iios
     * @return
     */
    @Override
    public List<ItemInOrder> findAll() {
        LOGGER.debug("ItemInOrderRestConsumer: findAll()");
        return restTemplate.getForEntity(url + "/all", List.class).getBody();
    }

    /**
     * add() new iio.
     * @param
     */
    @Override
    public void add(ItemInOrder iio) {
        LOGGER.debug("ItemInOrderRestConsumer: add({})", iio);
        restTemplate.postForEntity(url, iio, ItemInOrder.class);
    }

    /**
     * update() iio
     * @param
     */
    @Override
    public void update(ItemInOrder iio) {
        LOGGER.debug("ItemInOrderRestConsumer: update({})", iio);
        restTemplate.put(url, iio);

    }

    /**
     * delete() iio
     * @param
     */
    @Override
    public void delete(Integer iioOrderId, Integer iioItemId) {
        LOGGER.debug("ItemInOrderRestConsumer: delete({}{})", iioOrderId, iioItemId);
        restTemplate.delete(url + "/" + iioOrderId + "/" + iioItemId);
    }

    /**
     * findItemInOrderByDateTime()
     * @param
     * @return
     */
    @Override
    public List<ItemInOrder> findIioByOrderId(Integer iioOrderId) {
        LOGGER.debug("ItemInOrderRestConsumer: findIioByOrderId({})", iioOrderId);
        ItemInOrder[] itemInOrders = restTemplate.getForEntity(url + "/" + iioOrderId, ItemInOrder[].class).getBody();
        return Arrays.asList(itemInOrders);
    }

    /**
     * findItemInOrderById() iio
     * @param iioOrderId
     * @param iioItemId
     * @return
     */
    @Override
    public ItemInOrder findIioByOrderItemId(Integer iioOrderId, Integer iioItemId) {
        LOGGER.debug("ItemInOrderRestConsumer: findIioByOrderItemId({}{})", iioOrderId, iioItemId);
        return restTemplate.getForEntity(
                url + "/" + iioOrderId + "/" + iioItemId, ItemInOrder.class).getBody();
    }


}