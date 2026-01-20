
package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping(value = {"/hello", "/hi", "/"}, method = RequestMethod.GET)
    public String hello() {
        return "Hello World yyyyyyyy";
    }
}