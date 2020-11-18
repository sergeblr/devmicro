package com.epam.summer19;

import com.epam.summer19.model.Item;
import org.apache.camel.Endpoint;
import org.apache.camel.builder.RouteBuilder;

public class ItemRouterBuilder extends RouteBuilder {


    public Item someItem;

    @Override
    public void configure() throws Exception {
        from("http://localhost:8082/items/2")
                .convertBodyTo(Item.class);
    }

    public static void main(String[] args) {
        new Main().run(args);
        System.out.println("#### someItem: " + someItem);
    }
}
