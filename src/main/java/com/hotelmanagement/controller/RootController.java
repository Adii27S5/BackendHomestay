package com.hotelmanagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    @GetMapping("/")
    public String healthCheck() {
        return "TourNest Backend is running perfectly! API endpoints are available under /api";
    }
}
