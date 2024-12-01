package com.test.charity_api.repository;

import com.test.charity_api.entity.Donation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface DonationRepository extends JpaRepository<Donation, Long> {

    Page<Donation> findByCampaignIdOrderByCreatedAtDesc(Long campaignId, Pageable pageable);

    @Query("""
           SELECT d
           FROM Donation d
            LEFT JOIN d.donorName dn
           WHERE d.campaign.id = :campaignId
            AND (dn.name LIKE %:name% OR (d.showIdentity = true AND dn IS NULL AND d.donor.defaultName LIKE %:name%))
           ORDER BY d.createdAt DESC
           """)
    Page<Donation> findByCampaignIdAndDonorName(@Param("campaignId") Long campaignId, @Param("name") String name, Pageable pageable);

    @Query("""
           SELECT d
           FROM Donation d
           LEFT JOIN d.donorName dn
           WHERE d.campaign.id = :campaignId
           AND ((d.donorName.name LIKE %:name%) 
            OR (d.showIdentity = true AND dn IS NULL AND d.donor.defaultName LIKE %:name%)
            OR (d.showIdentity = false))
           ORDER BY d.createdAt DESC
           """)
    Page<Donation> findByCampaignIdAndDonorNameIncludeAnonymousName(@Param("campaignId") Long campaignId, @Param("name") String name, Pageable pageable);

    //?
    @Query("SELECT d FROM Donation d WHERE d.campaign.name LIKE %:campaignName% AND "
            + "(d.donorName.name LIKE %:name%) AND "
            + "d.createdAt BETWEEN :startDate AND :endDate")
    Page<Donation> findByCampaignIdAndDonorNameAndDateRange(@Param("campaignName") String campaignName,
            @Param("name") String name,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate,
            Pageable pageable);

    @Query("""
           SELECT d
           FROM Donation d
           WHERE d.donor.id = :donorId
           AND d.campaign.name LIKE %:campaignName%
           AND (:fromDate IS NULL AND :toDate IS NULL OR d.createdAt BETWEEN :fromDate AND :toDate)
           ORDER BY d.createdAt DESC
           """)
    Page<Donation> findByDonorIdAndCampaignNameAndDateRange(@Param("donorId") String donorId,
            @Param("campaignName") String campaignName,
            @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate,
            Pageable pageable);

}
