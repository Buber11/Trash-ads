package pl.pwr.trash.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.pwr.trash.request.UserRequest;
import pl.pwr.trash.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity login(
            HttpServletResponse httpServletResponse,
            @RequestBody @Valid UserRequest userRequest
    ){
        authService.login(
                httpServletResponse,
                userRequest);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/register")
    public ResponseEntity createUser(
            @RequestBody @Valid UserRequest userRequest
    ){
        authService.register(userRequest);
        return ResponseEntity.noContent().build();
    }
}
