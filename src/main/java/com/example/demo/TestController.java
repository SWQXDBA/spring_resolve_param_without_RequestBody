package com.example.demo;

import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @Data
    public static class Person{
        String name;
    }

    @PostMapping("json")
    public String json(Person person){
        return person.name;
    }
}
