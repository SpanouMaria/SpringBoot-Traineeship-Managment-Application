package com.traineeship.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "evaluations")
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private EvaluationType evaluationType;

    private int motivation;
    private int efficiency;
    private int effectiveness;
    private int skills;
    private int cooperation;

    @ManyToOne
    @JoinColumn(name = "traineeship_position_id")
    private TraineeshipPosition traineeshipPosition;

	private String text;
	
	public static String getEvaluationText(Evaluation evaluation) {
        return evaluation.text;
    }
}
