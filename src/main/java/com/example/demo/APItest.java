package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@RestController
@RequestMapping("/api")
public class APItest {

    ConcurrentMap<String, Ding> dings = new ConcurrentHashMap<>();

    @GetMapping("/{id}")
    public Ding getDing(String id) {
        return dings.get(id);
    }

    @GetMapping("/")
    public List<Ding> getAllDings() {
        return new ArrayList<>(dings.values());
    }

    @PostMapping("/")
    public Ding addDing(Ding ding) {
        dings.put(ding.getId(), ding);
        return ding;
    }


}


