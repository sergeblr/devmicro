package com.epam.summer19.web_app;

import com.epam.summer19.model.Item;
import com.epam.summer19.service.ItemService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Arrays;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:app-context-test.xml"})
class ItemControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired
    private ItemService itemService;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void listAllItemsTest() throws Exception {
        Mockito.when(itemService.findAll()).thenReturn(Arrays.asList(createItem(1)
                , createItem(2)));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/items")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.
                containsString("CafeMenu - Items list")));

        Mockito.verify(itemService, Mockito.times(1)).findAll();
    }

    @Test
    void gotoAddItemPageTest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/item")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers
                        .containsString("New item")));
    }

    @Test
    void AddItemTest() throws Exception {
        Mockito.doNothing().doThrow(new IllegalStateException())
                .when(itemService).add(createItem(1));

        mockMvc.perform(
                MockMvcRequestBuilders.post("/item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("itemId", "1")
                        .param("itemName", "Item1")
                        .param("itemPrice", "2.00")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/items"));

        Mockito.verify(itemService, Mockito.times(1)).add(Mockito.any(Item.class));
    }

    @Test
    void gotoEditItemPageTest() throws Exception {
        Mockito.when(itemService.findItemById(Mockito.anyInt())).thenReturn(createItem(2));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/item/1")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString("Edit item #")));

        Mockito.verify(itemService, Mockito.times(1)).findItemById(Mockito.anyInt());
    }

    @Test
    void updateItemTest() throws Exception {
        Mockito.doNothing().doThrow(new IllegalStateException())
                .when(itemService).update(createItem(1));

        mockMvc.perform(
                MockMvcRequestBuilders.post("/item/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("itemId", "1")
                        .param("itemName", "Item1")
                        .param("itemPrice", "2.00")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/items"));

        Mockito.verify(itemService, Mockito.times(1)).update(Mockito.any(Item.class));
    }

    @Test
    void deleteItemTest() throws Exception {
        Mockito.doNothing().doThrow(new IllegalStateException())
                .when(itemService).delete(Mockito.anyInt());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/item/1/delete")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/items"));

        Mockito.verify(itemService, Mockito.times(1)).delete(Mockito.anyInt());
    }

    private static Item createItem(Integer itemId) {
        Item item = new Item();
        item.setItemId(itemId);
        item.setItemName("Item"+itemId);
        item.setItemPrice(new BigDecimal(itemId+1.0));
        return item;
    }

}