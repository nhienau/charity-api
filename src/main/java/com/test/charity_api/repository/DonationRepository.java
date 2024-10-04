package com.test.charity_api.repository;

import com.test.charity_api.entity.Donation;
import jakarta.transaction.Transactional;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DonationRepository extends JpaRepository<Donation, Long> {

    @Query("SELECT d FROM Donation d WHERE d.campaign.id = :campaign_id")
    List<Donation> getDonation(@Param("campaign_id") Long campaignId);

    @Modifying
    @Transactional
    @Query(
            value
            = "INSERT INTO donation (campaign_id, donor_id, amount, created_at, transaction_id, donor_name_id)"
            + "VALUES (:campaign_id, :donor_id, :amount, :created_at, :transaction_id, :donor_name_id)",
            nativeQuery = true)
    void insert(@Param("campaign_id") int campaignId, @Param("donor_id") int donorId, @Param("amount") long amount,
            @Param("created_at") Date createdAt, @Param("transaction_id") String transactionId,
            @Param("donor_name_id") Integer donorNameId);
}
