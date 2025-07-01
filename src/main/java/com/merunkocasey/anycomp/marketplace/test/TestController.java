package com.merunkocasey.anycomp.marketplace.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/v1/test")
    public String test() {
        return "this is a test." ;
    }
}
