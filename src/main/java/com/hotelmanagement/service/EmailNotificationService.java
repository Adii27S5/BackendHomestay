package com.hotelmanagement.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * EmailNotificationService — Handles all HTML email communications for TourNest.
 * Renamed from EmailService to force IDE symbol re-indexing.
 */
@Service
public class EmailNotificationService {

    @Autowired
    private JavaMailSender mailSender;

    private void sendHtmlEmail(String to, String subject, String htmlBody) {
        if (to == null || to.isEmpty()) return;
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            // Set the sender for a professional feel
            String fromDisplay = "TourNest (No-Reply)";
            String fromEmail = "tournestofc@gmail.com"; 
            
            helper.setFrom(fromEmail, fromDisplay);
            helper.setTo(to);
            helper.setSubject(subject != null ? subject : "TourNest Notification");
            helper.setText(htmlBody != null ? htmlBody : "", true);
            
            mailSender.send(message);
        } catch (MailException | MessagingException | java.io.UnsupportedEncodingException e) {
            System.err.println("[EmailNotificationService] Email failed: " + e.getMessage());
        }
    }

    private String baseTemplate(String gradient, String icon, String heading, String body) {
        return "<!DOCTYPE html><html><head><meta charset='UTF-8'>" +
            "<style>*{margin:0;padding:0;box-sizing:border-box}body{font-family:-apple-system,BlinkMacSystemFont,'Segoe UI',sans-serif;background:#f0f4f8}" +
            ".w{max-width:600px;margin:40px auto;background:#fff;border-radius:24px;overflow:hidden;box-shadow:0 20px 60px rgba(0,0,0,.12)}" +
            ".h{background:linear-gradient(135deg," + gradient + ");padding:48px 40px;text-align:center}" +
            ".h h1{color:#fff;font-size:26px;font-weight:800}.h p{color:rgba(255,255,255,.8);font-size:14px;margin-top:8px}" +
            ".b{padding:40px}.b p{font-size:16px;line-height:1.7;color:#4a5568;margin-bottom:16px}" +
            ".f{background:#f7fafc;padding:24px 40px;text-align:center;border-top:1px solid #e2e8f0}" +
            ".f p{font-size:12px;color:#a0aec0}</style></head><body>" +
            "<div class='w'><div class='h'><div style='font-size:52px;margin-bottom:12px'>" + icon + "</div>" +
            "<h1>" + heading + "</h1><p>TourNest — Discover Incredible India</p></div>" +
            "<div class='b'>" + body + "</div>" +
            "<div class='f'><p>© 2026 TourNest · This is an automated email, please do not reply.</p></div></div></body></html>";
    }

    public void sendOtpEmail(String toEmail, String otp) {
        System.out.println("================================");
        System.out.println("GENERATED OTP FOR " + toEmail + " : " + otp);
        System.out.println("================================");
        String body = "<p>Use this secure OTP to reset your password:</p>" +
            "<div style='margin:32px 0;text-align:center'>" +
            "<div style='display:inline-block;background:linear-gradient(135deg,#667eea,#764ba2);border-radius:16px;padding:24px 48px'>" +
            "<span style='font-size:32px;font-weight:900;color:#fff;letter-spacing:8px;font-family:monospace'>" + otp + "</span></div></div>";
        sendHtmlEmail(toEmail, "TourNest — Your OTP Code", baseTemplate("#667eea,#764ba2", "🔐", "Reset OTP", body));
    }

    public void sendBookingApprovalEmail(String toEmail, String userName, String hotelName, String date, String guests, String price) {
        String body = "<p>Hello <strong>" + userName + "</strong>,</p>" +
            "<p>Your booking for <strong>" + hotelName + "</strong> has been <strong style='color:#38a169'>approved</strong>!</p>" +
            "<div style='background:#f7fafc;border-radius:12px;padding:24px;margin:16px 0;border-left:4px solid #38a169'>" +
            "<p style='margin-bottom:8px'><strong>Booking Details:</strong></p>" +
            "🏨 Hotel: " + hotelName + "<br>" +
            "📅 Dates: " + date + "<br>" +
            "👥 Members: " + guests + "<br>" +
            "💰 Total Amount: " + price + "</div>" +
            "<p>Thank you for choosing TourNest. We hope you have an incredible stay!</p>";
        sendHtmlEmail(toEmail, "TourNest — Booking Confirmed!", baseTemplate("#38a169,#2f855a", "✅", "Booking Approved!", body));
    }

    public void notifyReviewerOfPublishedReview(String toEmail, String userName, String attractionName) {
        String body = "<p>Hello <strong>" + userName + "</strong>,</p>" +
            "<p>Thank you for reviewing <strong>" + attractionName + "</strong>!</p>" +
            "<div style='background:#fffbeb;border-radius:12px;padding:16px;margin:16px 0;text-align:center'>" +
            "⭐⭐⭐⭐⭐<br>Your review is published!</div>";
        sendHtmlEmail(toEmail, "TourNest — Thank You for Your Review!", baseTemplate("#f6d365,#fda085", "📝", "Review Published", body));
    }

    public void sendFeedbackAcknowledgmentEmail(String toEmail, String userName) {
        String body = "<p>Hello <strong>" + userName + "</strong>,</p>" +
            "<p>We received your inquiry and our team will respond within 24 hours. 🚀</p>";
        sendHtmlEmail(toEmail, "TourNest — Support Receipt", baseTemplate("#4facfe,#00f2fe", "💬", "Message Received", body));
    }

    public void sendTourPublishedEmail(String toEmail, String tourTitle, String location) {
        String body = "<p>Congratulations! Your new experience <strong>" + tourTitle + "</strong> in <strong>" + location + "</strong> is now live on TourNest. 🚀</p>" +
            "<p>Travelers can now discover and book your story.</p>";
        sendHtmlEmail(toEmail, "TourNest — Your Experience is Live!", baseTemplate("#f6d365,#fda085", "✨", "Tour Published", body));
    }

    public void sendHomestayPublishedEmail(String toEmail, String stayTitle, String location) {
        String body = "<p>Congratulations! Your property <strong>" + stayTitle + "</strong> in <strong>" + location + "</strong> has been verified and is now live on TourNest. 🏠</p>" +
            "<p>Travelers can now discover and book your sanctuary.</p>";
        sendHtmlEmail(toEmail, "TourNest — Your Property is Live!", baseTemplate("#48c6ef,#6f86d6", "🏠", "Property Verified", body));
    }

    public void sendSupportInquiryToAdmin(String name, String email, String subject, String messageText) {
        String body = "<p><strong>Name:</strong> " + name + "</p>" +
            "<p><strong>Email:</strong> " + email + "</p>" +
            "<p><strong>Subject:</strong> " + subject + "</p>" +
            "<div style='background:#f7fafc;border-radius:12px;padding:16px;margin:16px 0;border-left:4px solid #4facfe'>" +
            messageText + "</div>" +
            "<p>Ensure to reply to the user directly at " + email + " to solve the query.</p>";
        sendHtmlEmail("tournestofc@gmail.com", "New Support Ticket: " + subject, baseTemplate("#4facfe,#00f2fe", "🎫", "New Support Ticket", body));
    }

    public void sendTourDeniedEmail(String toEmail, String tourTitle) {
        String body = "<p>Regarding your experience <strong>" + tourTitle + "</strong>,</p>" +
            "<p>We appreciate your submission, but it doesn't meet our current guidelines and has been denied. You can reach out to support for more details.</p>";
        sendHtmlEmail(toEmail, "TourNest — Experience Submission Update", baseTemplate("#f56565,#c53030", "🚫", "Submission Update", body));
    }

    public void sendHomestayDeniedEmail(String toEmail, String stayTitle) {
        String body = "<p>Regarding your property <strong>" + stayTitle + "</strong>,</p>" +
            "<p>We appreciate your submission, but it doesn't meet our platform standards at this time and has been denied.</p>";
        sendHtmlEmail(toEmail, "TourNest — Property Submission Update", baseTemplate("#f56565,#c53030", "🚫", "Submission Update", body));
    }
}
