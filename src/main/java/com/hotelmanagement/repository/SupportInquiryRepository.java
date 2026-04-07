package com.hotelmanagement.repository;

import com.hotelmanagement.model.SupportInquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupportInquiryRepository extends JpaRepository<SupportInquiry, Long> {
}
