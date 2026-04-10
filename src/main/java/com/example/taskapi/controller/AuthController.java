    package com.example.taskapi.controller;

    import com.example.taskapi.dto.LoginRequestDto;
    import com.example.taskapi.dto.LoginResponseDto;
    import com.example.taskapi.service.AuthService;
    import jakarta.validation.Valid;
    import org.springframework.web.bind.annotation.*;

    @RestController
    @RequestMapping("/api/auth")
    public class AuthController {

        private final AuthService authService;

        public AuthController(AuthService authService) {
            this.authService = authService;
        }

        @PostMapping("/login")
        public LoginResponseDto login(@Valid @RequestBody LoginRequestDto request) {
            return authService.login(request);
        }
    }