package com.epam.summer19.web_app_rabbit;

import com.epam.summer19.web_app_rabbit.consumers.ItemInOrderRestConsumer;
import com.epam.summer19.web_app_rabbit.consumers.ItemRestConsumer;
import com.epam.summer19.web_app_rabbit.consumers.OrderRestConsumer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@EnableRabbit
@SpringBootApplication
@Configuration
public class WebApplicationMicro {

    @Value("${rest.url}")
    String restUrl;

    @Value("${rest.items}")
    String restItems;

    @Value("${rest.orders}")
    String restOrders;

    @Value("${rest.iteminorders}")
    String restItemInOrders;

    /*RabbitMQ Props:*/
    @Value("${spring.rabbitmq.host}")
    String rabbitmqHost;

    @Value("${spring.rabbitmq.port}")
    int rabbitmqPort;

    @Value("${spring.rabbitmq.username}")
    String rabbitmqUsername;

    @Value("${spring.rabbitmq.password}")
    String rabbitmqPassword;

    /*RabbitMQ Items props*/
    @Value("${spring.rabbitmq.template.items.exchange}")
    String rabbitmqItemsExchange;

    @Value("${spring.rabbitmq.template.items.queue.getall}")
    String rabbitmqItemsGetAllQueue;

    @Value("${spring.rabbitmq.template.items.queue.add}")
    String rabbitmqItemsAddQueue;

    @Value("${spring.rabbitmq.template.items.queue.update}")
    String rabbitmqItemsUpdateQueue;

    @Value("${spring.rabbitmq.template.items.queue.delete}")
    String rabbitmqItemsDeleteQueue;

    @Value("${spring.rabbitmq.template.items.queue.findbyid}")
    String rabbitmqItemsFindByIdQueue;

    @Value("${spring.rabbitmq.template.items.queue.findbyname}")
    String rabbitmqItemsFindByNameQueue;

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

    /*RabbitMQ Props END*/


    @Autowired
    private ObjectMapper objectMapper;

    public static void main(String[] args) {
        SpringApplication.run(WebApplicationMicro.class, args);
    }


    /* RabbitMQ Config SENDER Start */
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
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory());
        return rabbitAdmin;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setReplyTimeout(60 * 1000);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
/*        rabbitTemplate.setExchange(rabbitmqExchange);*/
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter jsonMessageConverter()
    {
        return new Jackson2JsonMessageConverter();
    }

    /*RabbitMQ Items settings (exchange, queue & bindings create)*/
    @Bean
    public DirectExchange itemsExchange() {
        return new DirectExchange(rabbitmqItemsExchange);
    }

    @Bean
    public Queue itemsQueueGetAll() {
        return new Queue(rabbitmqItemsGetAllQueue, true);
    }

    @Bean
    public Queue itemsQueueAdd() {
        return new Queue(rabbitmqItemsAddQueue, true);
    }

    @Bean
    public Queue itemsQueueUpdate() {
        return new Queue(rabbitmqItemsUpdateQueue, true);
    }

    @Bean
    public Queue itemsQueueDelete() {
        return new Queue(rabbitmqItemsDeleteQueue, true);
    }

    @Bean
    public Queue itemsQueueFindById() {
        return new Queue(rabbitmqItemsFindByIdQueue, true);
    }

    @Bean
    public Queue itemsQueueFindByName() {
        return new Queue(rabbitmqItemsFindByNameQueue, true);
    }

    @Bean
    Binding bindingQueueGetAll(DirectExchange itemsExchange) {
        return BindingBuilder.bind(itemsQueueGetAll()).to(itemsExchange).with(rabbitmqItemsGetAllKey);
    }
    
    @Bean
    Binding bindingQueueAdd(DirectExchange itemsExchange) {
        return BindingBuilder.bind(itemsQueueAdd()).to(itemsExchange).with(rabbitmqItemsAddKey);
    }

    @Bean
    Binding bindingQueueUpdate(DirectExchange itemsExchange) {
        return BindingBuilder.bind(itemsQueueUpdate()).to(itemsExchange).with(rabbitmqItemsUpdateKey);
    }
    
    @Bean
    Binding bindingQueueDelete(DirectExchange itemsExchange) {
        return BindingBuilder.bind(itemsQueueDelete()).to(itemsExchange).with(rabbitmqItemsDeleteKey);
    }

    @Bean
    Binding bindingQueueFindById(DirectExchange itemsExchange) {
        return BindingBuilder.bind(itemsQueueFindById()).to(itemsExchange).with(rabbitmqItemsFindByIdKey);
    }
    
    @Bean
    Binding bindingQueueFindByName(DirectExchange itemsExchange) {
        return BindingBuilder.bind(itemsQueueFindByName()).to(itemsExchange).with(rabbitmqItemsFindByNameKey);
    }
    
    /*RabbitMQ Config END*/


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
