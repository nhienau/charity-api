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

    @Query("""
           SELECT c
           FROM Campaign c
           WHERE c.status = true
           AND c.name LIKE %:query%
           AND (:filter = 'all'
                OR (:filter = 'opening' AND c.closeDate > CURRENT_TIMESTAMP)
                OR (:filter = 'closed' AND c.closeDate < CURRENT_TIMESTAMP AND c.currentAmount < c.targetAmount)
                OR (:filter = 'fulfilled' AND c.currentAmount >= c.targetAmount))
           ORDER BY c.createdAt DESC
           """)
    Page<Campaign> getCampaigns(String query, String filter, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE Campaign c SET c.currentAmount = :amount WHERE c.id = :id")
    void updateCurrentAmount(@Param("id") int id, @Param("amount") long amount);
}
