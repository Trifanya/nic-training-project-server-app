package dev.trifanya.spring_webapp.unit.service;

import dev.trifanya.spring_webapp.activemq.producer.UserMessageProducer;
import dev.trifanya.spring_webapp.model.User;
import dev.trifanya.spring_webapp.service.UserService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserMessageProducer userMPMock;
    @InjectMocks
    private UserService testingService;

    private final int USER_ID = 1;
    private final int USER_COUNT = 3;

    @Test
    @SneakyThrows
    public void getUser_shouldReturnUser() {
        // Given
        User expectedUser = getUser(USER_ID);
        doAnswer(invocation -> getUser(invocation.getArgument(0))).when(userMPMock).sendGetUserMessage(anyInt());

        // When
        User resultUser = testingService.getUser(USER_ID);

        // Then
        assertEquals(expectedUser, resultUser);
        verify(userMPMock).sendGetUserMessage(USER_ID);
    }

    @Test
    @SneakyThrows
    public void getUsers_shouldFilterRequestParamsAndReturnUserList() {
        // Given
        List<User> expectedList = getUsers();
        Map<String, String> requestParams = getRequestParamsWithEmptyValues();
        Map<String, String> filteredRequestParams = getRequestParamsWithoutEmptyValues();
        when(userMPMock.sendGetUserListMessage(anyMap())).thenReturn(getUsers());

        // When
        List<User> resultList = testingService.getUsers(requestParams);

        // Then
        assertIterableEquals(expectedList, resultList);
        verify(userMPMock).sendGetUserListMessage(filteredRequestParams);
    }


    private User getUser(int userId) {
        return new User()
                .setId(userId).setName("TestName")
                .setSurname("TestSurname").setPosition("TestPosition")
                .setEmail("TestEmail").setPassword("TestPassword");
    }

    private List<User> getUsers() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < USER_COUNT; i++) {
            users.add(getUser(i));
        }
        return users;
    }

    private Map<String, String> getRequestParamsWithEmptyValues() {
        Map<String, String> filters = new HashMap<>();
        filters.put("testKey1", "testValue1");
        filters.put("testKey2", "");
        return filters;
    }

    private Map<String, String> getRequestParamsWithoutEmptyValues() {
        Map<String, String> filters = new HashMap<>();
        filters.put("testKey1", "testValue1");
        return filters;
    }
}
