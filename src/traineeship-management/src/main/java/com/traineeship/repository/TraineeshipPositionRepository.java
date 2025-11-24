package com.traineeship.repository;

import com.traineeship.model.TraineeshipPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface TraineeshipPositionRepository extends JpaRepository<TraineeshipPosition, Integer> {
    List<TraineeshipPosition> findByIsAssignedFalse();
    List<TraineeshipPosition> findByStatus(String status);
    List<TraineeshipPosition> findByStudentIsNotNull();

    @Query("SELECT t FROM TraineeshipPosition t " +
            "LEFT JOIN FETCH t.company " +
            "LEFT JOIN FETCH t.student " +
            "WHERE t.status = :status")
     List<TraineeshipPosition> findInProgressWithRelations(@Param("status") String status);
    
    @Query("SELECT DISTINCT t FROM TraineeshipPosition t LEFT JOIN FETCH t.evaluations e WHERE e IS NOT NULL")
    List<TraineeshipPosition> findWithAnyEvaluation();

    List<TraineeshipPosition> findByCompany_UsernameAndAssignedStudentIsNotNull(String username);

    
    List<TraineeshipPosition> findByStatusIn(List<String> statuses);
    

    List<TraineeshipPosition> findByCompanyUsername(String username);
}