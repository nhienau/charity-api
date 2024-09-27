package com.test.charity_api.repository;

import com.test.charity_api.entity.Donation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DonationRepository extends JpaRepository<Donation, Long> {

    @Query("SELECT d FROM Donation d WHERE d.campaign.id = :campaign_id")
    List<Donation> getDonation(@Param("campaign_id") Long campaignId);
}
