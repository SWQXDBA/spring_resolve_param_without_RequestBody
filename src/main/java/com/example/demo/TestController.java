package com.example.demo;

import lombok.Data;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("test")
public class TestController {

    @Data
    public static class Person{
        String name;
        Person son;
        int[] nums;
    }


    @PostMapping("json")
    public Person json( Person person){
        return person;
    }
}
