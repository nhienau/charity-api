package com.test.charity_api.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date closeDate;
    @Column(nullable = false)
    private long targetAmount;
    @Column(nullable = false)
    private long currentAmount;
    @Column(nullable = false)
    private int donationCount;
    @Column
    private Integer postId;
    @Column(nullable = false)
    private boolean status;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "campaign", cascade = CascadeType.ALL)
    private List<Donation> donation = new ArrayList<>();

    @Builder.Default
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "campaign", cascade = CascadeType.ALL)
    private List<CampaignImage> campaignImage = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private Account createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecturer", nullable = false)
    private Lecturer lecturer;
}
