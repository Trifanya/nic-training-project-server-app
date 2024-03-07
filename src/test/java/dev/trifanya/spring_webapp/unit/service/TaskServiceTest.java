package dev.trifanya.spring_webapp.unit.service;

import dev.trifanya.spring_webapp.model.task.Task;
import dev.trifanya.spring_webapp.activemq.producer.TaskMessageProducer;

import dev.trifanya.spring_webapp.service.TaskService;
import lombok.SneakyThrows;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @Mock
    private TaskMessageProducer taskMPMock;
    @InjectMocks
    private TaskService testingService;

    private final int TASK_ID = 1;

    @Test
    @SneakyThrows
    public void getTask_shouldReturnUser() {
        // Given
        Task expectedTask = new Task().setId(TASK_ID);
        doAnswer(invocation -> new Task().setId((invocation.getArgument(0))))
                .when(taskMPMock).sendGetTaskMessage(anyInt());

        // When
        Task resultTask = testingService.getTask(TASK_ID);

        // Then
        assertEquals(expectedTask, resultTask);
        verify(taskMPMock).sendGetTaskMessage(TASK_ID);
    }

    @Test
    @SneakyThrows
    public void getTasks_shouldFilterRequestParamsAndReturnUserList() {
        // Given
        List<Task> expectedList = getTaskList();
        Map<String, String> requestParams = getRequestParamsWithEmptyValues();
        Map<String, String> filteredRequestParams = getRequestParamsWithoutEmptyValues();
        when(taskMPMock.sendGetTaskListMessage(anyMap())).thenReturn(getTaskList());

        // When
        List<Task> resultList = testingService.getTasks(requestParams);

        // Then
        assertIterableEquals(expectedList, resultList);
        verify(taskMPMock).sendGetTaskListMessage(filteredRequestParams);
    }


    /** ВСПОМОГАТЕЛЬНЫЕ МЕТОДЫ */

    public List<Task> getTaskList() {
        return Arrays.asList(
                new Task().setId(11),
                new Task().setId(12),
                new Task().setId(13)
        );
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
