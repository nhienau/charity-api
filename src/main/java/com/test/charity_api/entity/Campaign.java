package com.test.charity_api.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Campaign {

    @Id
    @GeneratedValue
    private int id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date closeDate;
    @Column
    private long targetAmount;
    @Column
    private long currentAmount;
    @Column
    private int donationCount;
    @Column
    private int postId;
    @Column
    private boolean status;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "campaign", cascade = CascadeType.ALL)
    private List<Donation> donation = new ArrayList<>();

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "campaign", cascade = CascadeType.ALL)
    private List<CampaignImage> campaignImage = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private Account createdBy;
}
