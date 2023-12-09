package dev.trifanya.taskmanagementsystem.controller;

import dev.trifanya.taskmanagementsystem.dto.SignInRequest;
import dev.trifanya.taskmanagementsystem.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/authentication")
    public ResponseEntity<?> signIn(@RequestBody SignInRequest request) {
        System.out.println("Вызван метод AuthenticationController");
        String jwt = authenticationService.getJWT(request);

        return ResponseEntity.ok(jwt);
    }
}
