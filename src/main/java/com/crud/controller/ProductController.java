package com.crud.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/product")
public class ProductController {
    
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    String list() {
        return "List of products";
    }

    @RequestMapping(value = "/store", method = RequestMethod.POST)
    String store() {
        return "Product stored";
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    String update() {
        return "Product updated";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    String delete() {
        return "Product deleted";
    }

    @RequestMapping(value = "/list-one", method = RequestMethod.GET)
    String listOne() {
        return "List one product";
    }

}