package com.test.charity_api.repository;

import com.test.charity_api.entity.Campaign;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CampaignRepository extends JpaRepository<Campaign, Integer> {

    Campaign findByStatusTrueAndId(int id);

    Page<Campaign> findByStatusTrueAndNameContainingOrderByCreatedAtDesc(String query, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE Campaign c SET c.currentAmount = :amount WHERE c.id = :id")
    void updateCurrentAmount(@Param("id") int id, @Param("amount") long amount);
}
