package com.traineeship.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "students")
public class Student {
	
	

    @Id
    private String username;

    private String studentName;
    private String AM;
    private double avgGrade;
    private String preferredLocation;
    private String interests;
    private String skills;
    private boolean lookingForTraineeship;

    @OneToOne(mappedBy = "student")
    private TraineeshipPosition assignedTraineeship;

    @OneToOne
    @JoinColumn(name = "username", referencedColumnName = "username", insertable = false, updatable = false)
    private User user;
}
