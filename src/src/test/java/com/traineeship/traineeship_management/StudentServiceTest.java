package com.traineeship.traineeship_management;

import com.traineeship.model.Student;
import com.traineeship.model.TraineeshipPosition;
import com.traineeship.repository.StudentRepository;
import com.traineeship.repository.TraineeshipPositionRepository;
import com.traineeship.service.StudentService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class StudentServiceTest {

    @MockBean
    private StudentRepository studentRepository;
    @MockBean
    private TraineeshipPositionRepository positionRepository;

    @Autowired
    private StudentService studentService;

    @Test
    void applyToTraineeship_ShouldAssignPositionToStudent() {
        
        String studentUsername = "vasilis";
        Long traineeshipId = 1L;

        Student student = new Student();
        student.setUsername(studentUsername);

        TraineeshipPosition position = new TraineeshipPosition();
        position.setId(traineeshipId);

       
        when(studentRepository.findById(studentUsername)).thenReturn(Optional.of(student));
        when(positionRepository.findById(traineeshipId.intValue())).thenReturn(Optional.of(position));

       
        String studentUsername1 = "vasilis";
        Integer traineeshipId1 = 1;
        studentService.applyForTraineeship(studentUsername1, traineeshipId1);



        verify(positionRepository).save(position);

    }
}
