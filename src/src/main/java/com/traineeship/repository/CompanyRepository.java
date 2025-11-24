package com.traineeship.repository;

import com.traineeship.model.Company;
import com.traineeship.model.TraineeshipPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, String> {
    List<TraineeshipPosition> findByUsername(String username);
}