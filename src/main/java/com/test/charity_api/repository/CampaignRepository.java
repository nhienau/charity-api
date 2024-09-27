package com.test.charity_api.repository;

import com.test.charity_api.entity.Campaign;
import jakarta.transaction.Transactional;
import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CampaignRepository extends JpaRepository<Campaign, Integer> {

    @Modifying
    @Transactional
    @Query(
            value
            = "INSERT INTO campaign (name, description, close_date, target_amount, created_by) VALUES (:name, :description, :close_date, :target_amount, :created_by)",
            nativeQuery = true)
    void createCampaign(@Param("name") String name, @Param("description") String description,
            @Param("close_date") Date close_date, @Param("target_amount") long target_amount, @Param("created_by") int created_by);

    @Modifying
    @Transactional
    @Query("UPDATE Campaign c SET c.status = false WHERE c.id = :id")
    void deleteCampaign(@Param("id") int id);

    Campaign findByStatusTrueAndId(int id);

    Page<Campaign> findByStatusTrueAndNameContainingOrderByCreatedAtDesc(String query, Pageable pageable);
}
