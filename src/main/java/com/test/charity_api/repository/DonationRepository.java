package com.test.charity_api.repository;

import com.test.charity_api.entity.Donation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DonationRepository extends JpaRepository<Donation, Long> {

    Page<Donation> findByCampaignIdOrderByCreatedAtDesc(Long campaignId, Pageable pageable);

    @Query("SELECT d FROM Donation d WHERE d.campaign.id = :campaignId AND d.donorName.name LIKE %:name% ORDER BY d.createdAt DESC")
    Page<Donation> findByCampaignIdAndDonorName(@Param("campaignId") Long campaignId, @Param("name") String name, Pageable pageable);

    @Query("""
           SELECT d
           FROM Donation d
           LEFT JOIN d.donorName dn
           WHERE d.campaign.id = :campaignId
           AND ((d.donorName.name LIKE %:name%) OR (d.donorName IS NULL))
           ORDER BY d.createdAt DESC
           """)
    Page<Donation> findByCampaignIdAndDonorNameIncludeAnonymousName(@Param("campaignId") Long campaignId, @Param("name") String name, Pageable pageable);

}
