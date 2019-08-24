package com.epam.summer19.service;

import com.epam.summer19.model.Item;

import java.util.List;

public interface ItemService {

    /**
     * Add multiple items
     * @param items
     * @return
     */
    void add(Item... items);

    /**
     * Add item
     * @param item
     * @return
     */
    void add(Item item);

    /**
     * Update item
     * @param item
     */
    void update(Item item);

    /**
     * Delete item by id
     * @param itemId
     */
    void delete(Integer itemId);

    /**
     * List all items
     * @return
     */
    List<Item> findAll();

    /**
     * Find item by itemId
     * @param itemId
     * @return
     */
    Item findItemById(Integer itemId);
}