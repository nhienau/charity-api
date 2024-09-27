package com.test.charity_api.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
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
public class Account {

    @Id
    @GeneratedValue
    private int id;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private boolean status;
    
    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "createdBy", cascade = CascadeType.ALL)
    private List<Campaign> campaign = new ArrayList<>();
}
