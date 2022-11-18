package com.example.pokertest;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class TestAdmin {

    @GetMapping("/adOnly/{number}")
    public Integer get(@PathVariable(value = "number") Integer number) {
        return ++number;
    }
}
