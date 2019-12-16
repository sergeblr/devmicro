package com.epam.summer19.microrest_items;

import com.epam.summer19.model.Item;
import com.epam.summer19.service.ItemService;
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

}