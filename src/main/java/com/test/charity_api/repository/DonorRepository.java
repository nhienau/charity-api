package com.test.charity_api.repository;

import com.test.charity_api.entity.Donor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonorRepository extends JpaRepository<Donor, Long> {

    Donor findByStatusTrueAndPhoneNumber(String phoneNumber);
}
