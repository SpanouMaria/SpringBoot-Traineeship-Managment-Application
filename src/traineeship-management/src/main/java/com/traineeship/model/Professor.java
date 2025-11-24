package com.traineeship.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "professors")
public class Professor {

    @Column(name = "professor_name")
    private String professorName;

    @Id
    @Column(name = "username")
    private String username;


    @Column(name = "interests")
    private String interests;


    @OneToMany(mappedBy = "supervisor", cascade = CascadeType.ALL)
    private List<TraineeshipPosition> supervisedPositions;

    @OneToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
    private User user;
}
