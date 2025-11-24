package com.traineeship.traineeship_management;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;



import com.traineeship.model.User;
import com.traineeship.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;

    @Test
    void registerUser_ShouldCreateUser() throws Exception {
        mockMvc.perform(post("/users/register")
        	.with(csrf())
        	.with(user("admin").roles("ADMIN"))
            .param("username", "testuser")
            .param("password", "123456")
            .param("name", "Test User")
            .param("role", "STUDENT"))
        	.andExpect(status().is3xxRedirection())
        	.andExpect(redirectedUrl("/login")); 

        Optional<User> user = userRepository.findByUsername("testuser");
        assertTrue(user.isPresent());
    }
}

