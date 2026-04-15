package com.example.taskapi.controller;

import com.example.taskapi.entity.User;
import com.example.taskapi.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
class SecurityIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        userRepository.deleteAll();

        User normalUser = new User();
        normalUser.setName("Secure User");
        normalUser.setEmail("secure@example.com");
        normalUser.setPassword(passwordEncoder.encode("secret123"));
        normalUser.setRole("USER");
        userRepository.save(normalUser);

        User adminUser = new User();
        adminUser.setName("Admin User");
        adminUser.setEmail("admin@example.com");
        adminUser.setPassword(passwordEncoder.encode("admin123"));
        adminUser.setRole("ADMIN");
        userRepository.save(adminUser);
    }

    @Test
    void protectedEndpoint_shouldReturn401_withoutToken() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(containsString("Unauthorized")));
    }

    @Test
    void deleteUser_shouldReturn403_forUserRole() throws Exception {
        String userToken = loginAndGetToken("secure@example.com", "secret123");

        mockMvc.perform(delete("/api/users/1")
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isForbidden())
                .andExpect(content().string(containsString("Forbidden")));
    }

    @Test
    void deleteUser_shouldAllowAdminRole() throws Exception {
        User victimUser = new User();
        victimUser.setName("Victim User");
        victimUser.setEmail("victim@example.com");
        victimUser.setPassword(passwordEncoder.encode("victim123"));
        victimUser.setRole("USER");
        victimUser = userRepository.save(victimUser);

        String adminToken = loginAndGetToken("admin@example.com", "admin123");

        mockMvc.perform(delete("/api/users/" + victimUser.getId())
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().is2xxSuccessful());
    }

    private String loginAndGetToken(String email, String password) throws Exception {
        MvcResult result = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "email": "%s",
                                  "password": "%s"
                                }
                                """.formatted(email, password)))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("token").asText();
    }
}