package dev.trifanya.taskmanagementsystem.unit.service;

import dev.trifanya.taskmanagementsystem.model.User;
import dev.trifanya.taskmanagementsystem.dto.SignInRequest;
import dev.trifanya.taskmanagementsystem.security.jwt.JWTUtils;
import dev.trifanya.taskmanagementsystem.service.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Collection;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    private static final int USER_ID = 1;
    private static final String TEST_JWT = "Test JWT";
    private static final String TEST_EMAIL = "test@gmail.com";
    private static final String TEST_PASSWORD = "Test_passw0rd";

    @Mock
    private AuthenticationManager authManagerMock;
    @Mock
    private JWTUtils jwtUtilsMock;

    @InjectMocks
    private AuthenticationService testingService;

    @Test
    public void getJWT_shouldReturnJWT() {
        // Given
        mockAuthenticate();
        mockGenerateToken();
        // When
        String resultJWT = testingService.getJWT(getRequest());
        // Then
        Mockito.verify(authManagerMock).authenticate(getToken());
        Mockito.verify(jwtUtilsMock).generateToken(getUser());
        Assertions.assertEquals(TEST_JWT, resultJWT);

    }


    /**
     * Определение поведения mock-объектов.
     */

    private void mockAuthenticate() {
        Mockito.when(authManagerMock.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(getAuthentication());
    }

    private void mockGenerateToken() {
        Mockito.when(jwtUtilsMock.generateToken(any(User.class)))
                .thenReturn(TEST_JWT);
    }


    /**
     * Вспомогательные методы.
     */

    private User getUser() {
        return new User().setId(USER_ID);
    }

    private SignInRequest getRequest() {
        return new SignInRequest()
                .setEmail(TEST_EMAIL)
                .setPassword(TEST_PASSWORD);
    }

    private UsernamePasswordAuthenticationToken getToken() {
        return new UsernamePasswordAuthenticationToken(TEST_EMAIL, TEST_PASSWORD);
    }

    private Authentication getAuthentication() {
        return new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }
            @Override
            public Object getCredentials() {
                return null;
            }
            @Override
            public Object getDetails() {
                return null;
            }
            @Override
            public User getPrincipal() {
                return getUser();
            }
            @Override
            public boolean isAuthenticated() {
                return false;
            }
            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
            }
            @Override
            public String getName() {
                return null;
            }
        };
    }
}
