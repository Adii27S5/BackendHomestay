package com.hotelmanagement.service;

import com.hotelmanagement.model.SupportInquiry;
import com.hotelmanagement.repository.SupportInquiryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupportInquiryService {

    @Autowired
    private SupportInquiryRepository supportInquiryRepository;

    @Autowired
    private EmailNotificationService emailNotificationService;

    public SupportInquiry saveInquiry(SupportInquiry inquiry) {
        if (inquiry == null) throw new IllegalArgumentException("Inquiry cannot be null");
        SupportInquiry saved = supportInquiryRepository.save(inquiry);
        try {
            emailNotificationService.sendFeedbackAcknowledgmentEmail(saved.getEmail(), saved.getName());
            emailNotificationService.sendSupportInquiryToAdmin(saved.getName(), saved.getEmail(), saved.getSubject(), saved.getMessage());
        } catch (Exception e) {
            System.err.println("Failed to send support emails: " + e.getMessage());
        }
        return saved;
    }

    public List<SupportInquiry> getAllInquiries() {
        return supportInquiryRepository.findAll();
    }
}
