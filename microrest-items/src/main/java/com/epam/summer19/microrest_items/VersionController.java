package com.epam.summer19.microrest_items;

import com.epam.summer19.model.Item;
import com.epam.summer19.service.ItemService;
import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;

/**
 * Home MVC + cmd readme + index
 */
@CrossOrigin(origins = "*")
@PropertySource("classpath:application.properties")
@RestController
public class VersionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MicroRestItemsController.class);

    @Autowired
    private ItemService itemService;

    @Value("${restReadme}")
    private String restReadmeStr;

    @Value("${version}")
    private String versionStr;

    @Value("${index}")
    private String indexStr;

    @Value("${generatorok}")
    private String generatorOkStr;

    @GetMapping(value = "/version")
    public String version() {
        return versionStr;
    }

    @GetMapping(value = "/cafemenurest")
    public String restReadme() {
        return restReadmeStr;
    }

    @GetMapping(value = "/")
    public String index() {
        return indexStr;
    }

    /* JavaFaker: items */
    @GetMapping(value = "/generateitems/{quantity}")
    @ResponseStatus(value = HttpStatus.OK)
    public String genereateItems(@PathVariable("quantity") Integer quantity) {
        Faker faker = new Faker();
        Integer notUniqueRepeats = 0;
        long startTime = System.nanoTime();
        for(int i = 1; i < quantity; i++) {
            Item item = new Item();
            item.setItemName(faker.food().dish() + " " + faker.food().spice() + " " + RandomStringUtils.random(2,true,false));
            item.setItemPrice(new BigDecimal(BigInteger.valueOf(new Random().nextInt(10001)), 2));
/*            if(itemService.findItemByName(item.getItemName()) != null)
                LOGGER.debug("REST: VersionController -> JavaFaker item duplicate found: {}, continue...", item.getItemName());
            else itemService.add(item);*/
            try {
                itemService.add(item);
            } catch (Exception exc) {
                notUniqueRepeats++;
            }
        }
        long endTime = System.nanoTime();
        return generatorOkStr + quantity.toString() + ", Seconds: " + Double.toString((double)(endTime-startTime)/1000000000)
                + ", NotUnique repeats: " + notUniqueRepeats.toString();
    }

}