package com.traineeship.traineeship_management;

import com.traineeship.model.Application;
import com.traineeship.model.Student;
import com.traineeship.model.TraineeshipPosition;
import com.traineeship.repository.ApplicationRepository;
import com.traineeship.repository.StudentRepository;
import com.traineeship.repository.TraineeshipPositionRepository;
import com.traineeship.service.ApplicationService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ApplicationServiceTest {

    @MockBean
    private ApplicationRepository applicationRepository;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private TraineeshipPositionRepository positionRepository;

    @Autowired
    private ApplicationService applicationService;

    @Test
    void apply_ShouldCreateNewApplication() {
    
        Student student = new Student();
        student.setUsername("vasilis");

        TraineeshipPosition position = new TraineeshipPosition();
        position.setId((long) 77);

        Application application = new Application(null, student, position, "PENDING");

        when(applicationRepository.findByStudentAndPosition(student, position)).thenReturn(Optional.empty());
        when(applicationRepository.save(any(Application.class))).thenReturn(application);

        Application created = applicationService.apply(student, position);

        assertNotNull(created);
        assertEquals(student, created.getStudent());
        assertEquals(position, created.getPosition()); 
        assertEquals("PENDING", created.getStatus());
        verify(applicationRepository).save(any(Application.class));
    }

}
