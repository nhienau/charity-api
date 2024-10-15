package com.test.charity_api.repository;

import com.test.charity_api.entity.Donor;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonorRepository extends JpaRepository<Donor, Long> {

    Donor findByStatusTrueAndPhoneNumber(String phoneNumber);

    Optional<Donor> findById(String username);

    boolean existsById(String username);
}
