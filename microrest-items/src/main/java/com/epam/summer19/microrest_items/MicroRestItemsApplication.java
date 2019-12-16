package com.epam.summer19.microrest_items;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;

//@ComponentScan(basePackages = {"com.epam.summer19"})
@SpringBootApplication
@ImportResource(locations = {"classpath:test-db.xml"})
public class MicroRestItemsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroRestItemsApplication.class, args);
    }


        @Bean
        public Queue queue() {
            return new Queue("rpc.items.queue");
        }

        @Bean
        public DirectExchange exchange() {
            return new DirectExchange("rpc.items.exchange");
        }

        @Bean
        public ItemsRabbitConsumer server() {
            return new ItemsRabbitConsumer();
        }



/*        @Bean
        public Binding binding(DirectExchange exchange,
                               Queue queue) {
            return BindingBuilder.bind(queue)
                    .to(exchange)
                    .with("rpcitemskey");
        }*/


}
