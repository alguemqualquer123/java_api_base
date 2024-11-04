package com.fenix.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.client.RestClientException;

import DatabaseManager.DatabaseManager;
import types.Types;

@SpringBootApplication
@RestController
// @RequestMapping("/api") 
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
        DatabaseManager.createUsersTable();
    }

    @GetMapping("/")
    public String index() {
        return "Hello World!";
    }

    @PostMapping("/app")
    @RequestMapping(value = "/{name}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public String app(@RequestBody Types user) {
        System.out.println(user.getName() + " " + user.getAge());
        return user.getName() + " " + user.getAge();
    }

    // @GetMapping("/user")
    // @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    // @ResponseStatus(HttpStatus.OK)
    // public Map<String, Object> user(@RequestParam String name, int age) {
    //     System.out.println(name + " " + age);
    //     return DatabaseManager.updateUser(name, age);
    // }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public ArrayList<Map<String, Object>> getUsers() {
        System.out.println("getUsers: " + DatabaseManager.getUserss());
        return DatabaseManager.getUserss();
    }

    @GetMapping("/fetch")
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping( value = "/{name}",method = RequestMethod.GET)
    public String fetch() {
        String url = "http://localhost:8000/users";	
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                return "Error: " + response.getStatusCode();
            }

        } catch (RestClientException e) {
            System.out.println("Error: " + e.getMessage());
            return "Error fetching data";
        }
    }
}
