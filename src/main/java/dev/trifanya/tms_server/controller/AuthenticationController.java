/*
package dev.trifanya.taskmanagementsystem.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import dev.trifanya.taskmanagementsystem.dto.SignInRequest;
import dev.trifanya.taskmanagementsystem.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authentication")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<?> signIn(@RequestBody @Valid SignInRequest request) {
        String jwt = authenticationService.getJWT(request);
        return ResponseEntity.ok(jwt);
    }
}
*/
