package com.traineeship.traineeship_management;

import com.traineeship.model.TraineeshipPosition;
import com.traineeship.model.Company;
import com.traineeship.model.Student;
import com.traineeship.model.User;
import com.traineeship.repository.ProfessorRepository;
import com.traineeship.repository.StudentRepository;
import com.traineeship.repository.TraineeshipPositionRepository;
import com.traineeship.service.TraineeshipCommitteeService;
import com.traineeship.strategy.ProfessorAssignmentStrategy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.traineeship.service.TraineeshipCommitteeService;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TraineeshipCommitteeServiceTest {
    @Autowired
    private TraineeshipCommitteeService traineeshipCommitteeService;

    @MockBean
    private StudentRepository studentRepository;
    @MockBean
    private TraineeshipPositionRepository traineeshipPositionRepository;
    
    @MockBean
    private ProfessorRepository professorRepository;


    @MockBean
    private ProfessorAssignmentStrategy professorAssignmentStrategy;

    

    @Test
    void searchTraineeshipPositions_ShouldReturnMatchingPositions() {
        // Arrange
        String studentUsername = "teststudent";
        String strategy = "INTEREST_BASED"; 

        // mock Student
        Student student = new Student();
        student.setUsername(studentUsername);
        student.setInterests("Java,Spring");
        student.setSkills("OOP,Databases");

        TraineeshipPosition pos1 = new TraineeshipPosition();
        pos1.setTitle("Backend Java Intern");
        pos1.setTopics("Java,Spring,REST");
        pos1.setSkills("OOP,Git");

        TraineeshipPosition pos2 = new TraineeshipPosition();
        pos2.setTitle("Frontend React Intern");
        pos2.setTopics("JavaScript,React");
        pos2.setSkills("HTML,CSS");

        TraineeshipPosition pos3 = new TraineeshipPosition();
        pos3.setTitle("Full Stack Developer");
        pos3.setTopics("Java,Spring,JavaScript");
        pos3.setSkills("OOP,Databases,Git");

        Company mockCompany = new Company();
        mockCompany.setCompanyLocation("Athens");
        mockCompany.setCompanyName("MockCo");
        mockCompany.setUsername("mockco");

        pos1.setCompany(mockCompany);
        pos2.setCompany(mockCompany);
        pos3.setCompany(mockCompany);


        List<TraineeshipPosition> mockPositions = Arrays.asList(pos1, pos2, pos3);

        when(studentRepository.findById(studentUsername)).thenReturn(Optional.of(student));
        when(traineeshipPositionRepository.findAll()).thenReturn(mockPositions);

        // Act
        List<TraineeshipPosition> found = traineeshipCommitteeService.searchTraineeshipsForStudent(studentUsername);

        // Assert
        assertNotNull(found);
        assertTrue(found.stream().anyMatch(p -> p.getTitle().equals("Backend Java Intern")));
        assertFalse(found.stream().anyMatch(p -> p.getTitle().equals("Frontend React Intern")));
        assertTrue(found.stream().anyMatch(p -> p.getTitle().equals("Full Stack Developer")));
    }
}
