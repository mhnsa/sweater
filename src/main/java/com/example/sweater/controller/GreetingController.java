package com.example.sweater.controller;

import com.example.sweater.domain.Message;
import com.example.sweater.repository.MessageRepository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class GreetingController {

    @Autowired
    private MessageRepository repository;

    @GetMapping("/greeting")
    public String greeting(
            @RequestParam(name = "name", required = false, defaultValue = "World") String name,
            Map<String, Object> model) {

        model.put("name", name);
        return "greeting";
    }

    @GetMapping
    public String main(Map<String, Object> model) {
        Iterable<Message> messages = repository.findAll();

        model.put("messages", messages);
        return "main";
    }

    @PostMapping
    public String add(@RequestParam String text,
                      @RequestParam String tag,
                      Map<String, Object> model) {
        var message = new Message(text, tag);

        repository.save(message);

        Iterable<Message> messages = repository.findAll();

        model.put("messages", messages);

        return "main";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter,
                         Map<String, Object> model) {
        Iterable<Message> messages;

        if(StringUtils.isNotBlank(filter)) {
            messages = repository.findByTag(filter);
        } else {
            messages = repository.findAll();
        }
        model.put("messages", messages);

        return "main";
    }


}
