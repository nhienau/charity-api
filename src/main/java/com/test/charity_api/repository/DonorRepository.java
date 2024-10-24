package com.test.charity_api.repository;

import com.test.charity_api.entity.Donor;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DonorRepository extends JpaRepository<Donor, String> {

    Donor findByStatusTrueAndPhoneNumber(String phoneNumber);
    
    Page<Donor> findByStatusTrueAndIdContaining(String query, Pageable pageable);
    
    Donor findByStatusTrueAndId(String id);

    Optional<Donor> findById(String username);
    
    @Query("SELECT d FROM Donor d WHERE d.id like :username")
    Donor FindUser(@Param("username") String username);

    boolean existsById(String username);
    
    @Modifying
    @Transactional
    @Query("UPDATE Donor d SET d.password = :newPassword WHERE d.id = :username")
    void updatePassword(@Param("username") String username, @Param("newPassword") String newPassword) throws Exception;
    
    @Modifying
    @Transactional
    @Query("UPDATE Donor d SET d.status = false WHERE d.id = :id")
    void deleteDonor(@Param("id") String id);
}
