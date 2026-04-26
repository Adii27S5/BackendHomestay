package com.hotelmanagement.controller;

import com.hotelmanagement.config.DataSeeder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/system")
public class SystemController {

    @Autowired
    private DataSeeder dataSeeder;

    @GetMapping("/reseed")
    public String reseed() {
        try {
            dataSeeder.run();
            return "SUCCESS: Ecosystem force-reset and re-seeded with latest visuals.";
        } catch (Exception e) {
            return "FAILURE: " + e.getMessage();
        }
    }
}
