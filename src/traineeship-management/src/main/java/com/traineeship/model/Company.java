package com.traineeship.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "companies")
public class Company {

    @Id
    private String username;

    private String companyName;
    private String companyLocation;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<TraineeshipPosition> positions;

    @OneToOne
    @JoinColumn(name = "username", referencedColumnName = "username", insertable = false, updatable = false)
    private User user;
}
