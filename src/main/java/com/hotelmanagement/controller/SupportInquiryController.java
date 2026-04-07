package com.hotelmanagement.controller;

import com.hotelmanagement.model.SupportInquiry;
import com.hotelmanagement.service.SupportInquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/support")
@CrossOrigin(origins = "*")
public class SupportInquiryController {

    @Autowired
    private SupportInquiryService supportInquiryService;


    @PostMapping
    public SupportInquiry createInquiry(@RequestBody SupportInquiry inquiry) {
        return supportInquiryService.saveInquiry(inquiry);
    }

    @GetMapping
    public List<SupportInquiry> getAllInquiries() {
        return supportInquiryService.getAllInquiries();
    }
}
