package com.traineeship.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Data
@Entity
@Table(name = "traineeship_positions")
public class TraineeshipPosition {
	
	@ManyToOne
	@JoinColumn(name = "assigned_student_username", referencedColumnName = "username")
	private Student assignedStudent;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String topics;
    private String skills;
    private boolean isAssigned;
    private String studentLogbook;
    private boolean passFailGrade;

    @OneToOne
    @JoinColumn(name = "student_username")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "professor_username")
    private Professor supervisor;

    @ManyToOne
    @JoinColumn(name = "company_username")
    private Company company;

    @OneToMany(mappedBy = "traineeshipPosition")
    private List<Evaluation> evaluations;
    
    @Column(name = "status") 
    private String status;
    
}
