package com.example.pokertest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/user")
public class TestUser {

    private static final String template = "%s, %s!";
    private final AtomicLong counter = new AtomicLong();

    @PutMapping("/inc/{number}")
    public ResponseEntity<Integer> put(@PathVariable(value = "number") Integer number) {
        return ResponseEntity.ok(++number);
    }

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name,
                             @RequestParam(value = "greet", defaultValue = "Hello") String greet) {
        return new Greeting(counter.incrementAndGet(), String.format(template, greet, name));
    }

    @PostMapping("/postint")
    public Integer post(@RequestBody Integer number) {
        return number;
    }

    @DeleteMapping("/del/{id}")
    public Integer delete(@PathVariable(value = "id") Integer number) {
        return ++number;
    }
}
