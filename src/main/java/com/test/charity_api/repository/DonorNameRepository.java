package com.test.charity_api.repository;

import com.test.charity_api.entity.DonorName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DonorNameRepository extends JpaRepository<DonorName, Long> {

    @Query("SELECT dn FROM DonorName dn WHERE dn.donor.id = :donorId AND dn.name = :name")
    DonorName findByNameAndDonorId(@Param("name") String name, @Param("donorId") String donorId);

    @Query("""
           SELECT ELEMENT(d.donorName)
           FROM Donor d
           WHERE d.status = true AND d.phoneNumber LIKE :phoneNumber
           ORDER BY ELEMENT(d.donorName).name ASC
           """)
    Page<DonorName> getDonorNamesByPhoneNumber(@Param("phoneNumber") String phoneNumber, Pageable pageable);

    DonorName findById(int id);
}
