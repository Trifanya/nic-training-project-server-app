package dev.trifanya.tms_server.unit.service;

import dev.trifanya.tms_server.model.User;
import dev.trifanya.tms_server.service.UserService;
import dev.trifanya.tms_server.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private static final int USER_ID = 1;
    private static final String USER_EMAIL = "test1@gmail.com";
    private static final String ENCODED_PASSWORD = "Encoded_passw0rd";

    @Mock
    private UserRepository userRepoMock;
    @Mock
    private PasswordEncoder encoderMock;

    @InjectMocks
    private UserService testingService;

    @Test
    public void loadUserByUsername_userIsExist_shouldReturnUser() {
        // Given
        mockFindByEmail_exist();
        // When
        User resultUser = testingService.loadUserByUsername(USER_EMAIL);
        // Then
        Mockito.verify(userRepoMock).findByEmail(USER_EMAIL);
        Assertions.assertEquals(getUser(USER_ID, USER_EMAIL), resultUser);
    }

    @Test
    public void loadUserByUsername_userIsNotExist_shouldThrowException() {
        // Given
        mockFindByEmail_notExist();
        // When // Then
        Assertions.assertThrows(NotFoundException.class, () -> testingService.loadUserByUsername(USER_EMAIL));
    }

    @Test
    public void getUser_userIsExist_shouldReturnUser() {
        // Given
        mockFindById_exist();
        // When
        User resultUser = testingService.getUser(USER_ID);
        // Then
        Mockito.verify(userRepoMock).findById(USER_ID);
        Assertions.assertEquals(getUser(USER_ID, USER_EMAIL), resultUser);
    }

    @Test
    public void getUser_userIsNotExist_shouldThrowException() {
        // Given
        mockFindById_notExist();
        // When // Then
        Assertions.assertThrows(NotFoundException.class, () -> testingService.getUser(USER_ID));
    }

    @Test
    public void createNewUser_shouldAssignIdAndEncodedPassword() {
        // Given
        User userToSave = getUser(0, USER_EMAIL);
        mockEncode();
        mockSaveNew();
        // When
        User resultUser = testingService.createNewUser(userToSave);
        // Then
        Mockito.verify(encoderMock).encode(anyString());
        Mockito.verify(userRepoMock).save(userToSave);
        Assertions.assertNotNull(resultUser.getId());
        Assertions.assertEquals(ENCODED_PASSWORD, resultUser.getPassword());
    }

    @Test
    public void updateUserInfo_shouldAssignIdAndEncodedPassword() {
        // Given
        User updatedUser = getUser(0, USER_EMAIL);
        mockEncode();
        mockSaveUpdated();
        // When
        User resultUser = testingService.updateUserInfo(USER_ID, updatedUser);
        // Then
        Mockito.verify(encoderMock).encode(anyString());
        Mockito.verify(userRepoMock).save(updatedUser);
        Assertions.assertEquals(USER_ID, resultUser.getId());
        Assertions.assertEquals(ENCODED_PASSWORD, resultUser.getPassword());
    }

    @Test
    public void deleteUser() {
        // When
        testingService.deleteUser(USER_ID);
        // Then
        Mockito.verify(userRepoMock).deleteById(USER_ID);
    }


    /**
     * Определение поведения mock-объектов.
     */

    private void mockFindByEmail_exist() {
        Mockito.doAnswer(invocationOnMock -> Optional.of(getUser(USER_ID, invocationOnMock.getArgument(0))))
                .when(userRepoMock).findByEmail(anyString());
    }

    private void mockFindByEmail_notExist() {
        Mockito.when(userRepoMock.findByEmail(anyString())).thenReturn(Optional.empty());
    }

    private void mockFindById_exist() {
        Mockito.doAnswer(invocationOnMock -> Optional.of(getUser(invocationOnMock.getArgument(0), USER_EMAIL)))
                .when(userRepoMock).findById(anyInt());
    }

    private void mockFindById_notExist() {
        Mockito.when(userRepoMock.findById(anyInt())).thenReturn(Optional.empty());
    }

    private void mockSaveNew() {
        Mockito.doAnswer(
                invocationOnMock -> {
                    User user = invocationOnMock.getArgument(0);
                    user.setId(USER_ID);
                    return user;
                }).when(userRepoMock).save(any(User.class));
    }

    private void mockSaveUpdated() {
        Mockito.doAnswer(invocationOnMock -> invocationOnMock.getArgument(0))
                .when(userRepoMock).save(any(User.class));
    }

    private void mockEncode() {
        Mockito.when(encoderMock.encode(anyString())).thenReturn(ENCODED_PASSWORD);
    }


    /**
     * Вспомогательные методы.
     */

    private User getUser(int userId, String email) {
        return new User()
                .setId(userId)
                .setEmail(email)
                .setPassword("********");
    }

}
