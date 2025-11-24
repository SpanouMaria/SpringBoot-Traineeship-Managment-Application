package com.traineeship.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "applications")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_username", referencedColumnName = "username")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "position_id", referencedColumnName = "id")
    private TraineeshipPosition position;

    @Column(name = "status")
    private String status; 
}
