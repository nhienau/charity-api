package com.test.charity_api.repository;

import com.test.charity_api.entity.CampaignImage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CampaignImageRepository extends JpaRepository<CampaignImage, Integer> {

    List<CampaignImage> findByCampaignId(Long campaignId);

    @Transactional
    void deleteByIdIn(List<Integer> ids);

    @Query("SELECT ci FROM CampaignImage ci WHERE ci.id IN :ids")
    List<CampaignImage> findByIdsIn(@Param("ids") List<Integer> ids);
}
