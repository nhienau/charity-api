package com.test.charity_api.repository;

import com.test.charity_api.entity.DonorName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DonorNameRepository extends JpaRepository<DonorName, Long> {

    @Query("SELECT dn FROM DonorName dn WHERE dn.donor.id = :donorId AND dn.name = :name")
    DonorName findByNameAndDonorId(@Param("name") String name, @Param("donorId") String donorId);
}
