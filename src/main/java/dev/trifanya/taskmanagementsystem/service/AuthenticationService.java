package dev.trifanya.taskmanagementsystem.service;

import dev.trifanya.taskmanagementsystem.dto.SignInRequest;
import dev.trifanya.taskmanagementsystem.model.User;
import dev.trifanya.taskmanagementsystem.security.jwt.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final JWTUtils jwtUtils;

    public String getJWT(SignInRequest request) {

        //System.out.println("Вызван AuthenticationService");

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                ));
        User user = (User) authentication.getPrincipal();

        //System.out.println("ИО: " + user.getName() + " " + user.getSurname());

        return jwtUtils.generateToken(user);
    }
}
