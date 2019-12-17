package com.epam.summer19.web_app;

import com.epam.summer19.web_app.consumers.ItemInOrderRestConsumer;
import com.epam.summer19.web_app.consumers.ItemRestConsumer;
import com.epam.summer19.web_app.consumers.OrderRestConsumer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class WebApplication {

    @Value("${rest.url}")
    String restUrl;

    @Value("${rest.items}")
    String restItems;

    @Value("${rest.orders}")
    String restOrders;

    @Value("${rest.iteminorders}")
    String restItemInOrders;

    /*RabbitMQ properties*/
    @Value("${spring.rabbitmq.host}")
    String rabbitmqHost;

    @Value("${spring.rabbitmq.port}")
    int rabbitmqPort;

    @Value("${spring.rabbitmq.username}")
    String rabbitmqUsername;

    @Value("${spring.rabbitmq.password}")
    String rabbitmqPassword;


    @Autowired
    private ObjectMapper objectMapper;

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    /* RabbitMQ Config: */
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(rabbitmqHost);
        connectionFactory.setPort(rabbitmqPort);
        connectionFactory.setUsername(rabbitmqUsername);
        connectionFactory.setPassword(rabbitmqPassword);
        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange("rpc.items.exchange");
    }

    @Bean
    public Queue queue() {
        return new Queue("rpc.items.queue");
    }





    @Bean
    public ItemRestConsumer itemService() {
        ItemRestConsumer itemService = new ItemRestConsumer(restUrl+restItems, restTemplate());
        return itemService;
    }

    @Bean
    public OrderRestConsumer orderService() {
        OrderRestConsumer orderService = new OrderRestConsumer(restUrl+restOrders, restTemplate());
        return orderService;
    }

    @Bean
    public ItemInOrderRestConsumer itemInOrderService() {
        ItemInOrderRestConsumer itemInOrderService = new ItemInOrderRestConsumer(restUrl+restItemInOrders, restTemplate());
        return itemInOrderService;
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter jsonMessageConverter = new MappingJackson2HttpMessageConverter();
        jsonMessageConverter.setObjectMapper(objectMapper);
        messageConverters.add(jsonMessageConverter);
        restTemplate.setMessageConverters(messageConverters);
        return restTemplate;
    }

}
