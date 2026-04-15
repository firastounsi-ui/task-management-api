package com.example.taskapi.service;

import com.example.taskapi.dto.LoginRequestDto;
import com.example.taskapi.dto.LoginResponseDto;
import com.example.taskapi.dto.RegisterRequestDto;
import com.example.taskapi.dto.UserResponseDto;
import com.example.taskapi.entity.User;
import com.example.taskapi.exception.DuplicateEmailException;
import com.example.taskapi.repository.UserRepository;
import com.example.taskapi.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @Test
    void register_shouldCreateUserSuccessfully() {
        RegisterRequestDto request = new RegisterRequestDto();
        request.setName("New User");
        request.setEmail("newuser@example.com");
        request.setPassword("secret123");

        when(userRepository.findByEmail("newuser@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("secret123")).thenReturn("hashedPassword");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setName("New User");
        savedUser.setEmail("newuser@example.com");
        savedUser.setPassword("hashedPassword");
        savedUser.setRole("USER");

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserResponseDto response = authService.register(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("New User", response.getName());
        assertEquals("newuser@example.com", response.getEmail());
        assertEquals("USER", response.getRole());

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User userToSave = userCaptor.getValue();
        assertEquals("New User", userToSave.getName());
        assertEquals("newuser@example.com", userToSave.getEmail());
        assertEquals("hashedPassword", userToSave.getPassword());
        assertEquals("USER", userToSave.getRole());
    }

    @Test
    void register_shouldThrowDuplicateEmailException_whenEmailAlreadyExists() {
        RegisterRequestDto request = new RegisterRequestDto();
        request.setName("New User");
        request.setEmail("existing@example.com");
        request.setPassword("secret123");

        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setEmail("existing@example.com");

        when(userRepository.findByEmail("existing@example.com")).thenReturn(Optional.of(existingUser));

        assertThrows(DuplicateEmailException.class, () -> authService.register(request));

        verify(userRepository, never()).save(any(User.class));
        verify(passwordEncoder, never()).encode(anyString());
    }

    @Test
    void login_shouldReturnToken_whenCredentialsAreValid() {
        LoginRequestDto request = new LoginRequestDto();
        request.setEmail("secure@example.com");
        request.setPassword("secret123");

        User user = new User();
        user.setId(1L);
        user.setName("Secure User");
        user.setEmail("secure@example.com");
        user.setPassword("hashedPassword");
        user.setRole("USER");

        when(userRepository.findByEmail("secure@example.com")).thenReturn(Optional.of(user));
        when(jwtService.generateToken("secure@example.com", "USER")).thenReturn("jwt-token");

        LoginResponseDto response = authService.login(request);

        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService).generateToken("secure@example.com", "USER");
    }
}