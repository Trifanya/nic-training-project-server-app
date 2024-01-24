package dev.trifanya.tms_server.unit.service;

import dev.trifanya.tms_server.model.User;
import dev.trifanya.tms_server.model.task.Task;
import dev.trifanya.tms_server.service.TaskService;
import dev.trifanya.tms_server.repository.TaskRepository;
import dev.trifanya.tms_server.exception.NotFoundException;
import dev.trifanya.tms_server.service.specification.TaskSpecificationConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.*;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    private static final int TASK_ID = 1;
    private static final int AUTHOR_ID = 1;
    private static final int PAGE_NUMBER = 1;
    private static final int PERFORMER_ID = 2;
    private static final int ITEMS_PER_PAGE = 3;
    private static final String SORT_BY = "id";
    private static final String SORT_DIR_ASC = "ASC";
    private static final String UPDATED_TITLE = "Updated title";
    private static final String FIELD_NAME_AUTHOR = "author";
    private static final String FIELD_NAME_PERFORMER = "performer";
    private static final LocalDateTime CREATED_AT = LocalDateTime.parse("2023-12-10T15:52:00");

    @Mock
    private TaskRepository taskRepoMock;
    @Mock
    private TaskSpecificationConstructor constructorMock;

    @InjectMocks
    private TaskService testingService;

    @Test
    public void getTask_taskIsExist_shouldReturnTask() {
        // Given
        mockFindById_exist();

        // When
        Task resultTask = testingService.getTask(TASK_ID);

        // Then
        Mockito.verify(taskRepoMock).findById(TASK_ID);
        Assertions.assertEquals(getTask(TASK_ID), resultTask);
    }

    @Test
    public void getTask_taskIsNotExist_shouldThrowException() {
        // Given
        mockFindById_notExist();

        // When // Then
        Assertions.assertThrows(NotFoundException.class, () -> testingService.getTask(TASK_ID));
    }

    @Test
    public void getTasksByAuthor_shouldFetchPageFilters() {
        // Given
        User author = getUser(AUTHOR_ID);
        Map<String, String> expectedFilters = new HashMap<>();
        expectedFilters.put("remainingKey", "remainingValue");
        mockFindAll();

        // When
        testingService.getTasksByAuthor(author, getFilters());

        // Then
        Mockito.verify(constructorMock).createTaskSpecification(FIELD_NAME_AUTHOR, author, expectedFilters);
    }

    @Test
    public void getTasksByAuthor_shouldReturnTaskList() {
        // Given
        mockFindAll();

        // When
        List<Task> resultTaskList = testingService.getTasksByAuthor(getUser(AUTHOR_ID), getFilters());

        // Then
        Mockito.verify(taskRepoMock).findAll((Specification<Task>) null, getPageRequest());
        Assertions.assertIterableEquals(getTaskList(), resultTaskList);
    }

    @Test
    public void getTasksByPerformer_shoudFetchPageFilters() {
        // Given
        User performer = getUser(PERFORMER_ID);
        Map<String, String> expectedFilters = new HashMap<>();
        expectedFilters.put("remainingKey", "remainingValue");
        mockFindAll();

        // When
        testingService.getTasksByPerformer(performer, getFilters());

        // Then
        Mockito.verify(constructorMock).createTaskSpecification(FIELD_NAME_PERFORMER, performer, expectedFilters);
    }

    @Test
    public void getTasksByPerformer_shouldReturnTaskList() {
        // Given
        mockFindAll();

        // When
        List<Task> resultTaskList = testingService.getTasksByPerformer(getUser(PERFORMER_ID), getFilters());

        // Then
        Mockito.verify(taskRepoMock).findAll((Specification<Task>) null, getPageRequest());
        Assertions.assertIterableEquals(getTaskList(), resultTaskList);
    }

    @Test
    public void createNewTask_shouldAssignIdAndCreatedAtAndAuthorAndPerformer() {
        // Given
        Task taskToSave = getTask(TASK_ID);
        User author = getUser(AUTHOR_ID);
        User performer = getUser(PERFORMER_ID);
        mockSaveNew();

        // When
        Task resultTask = testingService.createNewTask(author, performer, taskToSave);

        // Then
        Mockito.verify(taskRepoMock).save(taskToSave);
        Assertions.assertNotNull(resultTask.getId());
        Assertions.assertNotNull(resultTask.getCreatedAt());
        Assertions.assertEquals(author, resultTask.getAuthor());
        Assertions.assertEquals(performer, resultTask.getPerformer());
    }

    @Test
    public void updateTaskInfo_shouldNotUpdateIdAndAuthorAndPerformer() {
        // Given
        Task updatedTask = new Task().setId(TASK_ID).setTitle(UPDATED_TITLE);
        mockFindById_exist();
        mockSaveUpdated();

        // When
        Task resultTask = testingService.updateTaskInfo(updatedTask);

        // Then
        Mockito.verify(taskRepoMock).save(updatedTask);
        Assertions.assertEquals(CREATED_AT, resultTask.getCreatedAt());
        Assertions.assertEquals(getUser(AUTHOR_ID), resultTask.getAuthor());
        Assertions.assertEquals(getUser(PERFORMER_ID), resultTask.getPerformer());
        Assertions.assertEquals(UPDATED_TITLE, resultTask.getTitle());
    }

    @Test
    public void deleteTask() {
        // When
        testingService.deleteTask(TASK_ID);

        // Then
        Mockito.verify(taskRepoMock).deleteById(TASK_ID);
    }


    /**
     * Определение поведения mock-объектов.
     */

    private void mockFindById_exist() {
        Mockito.doAnswer(invocationOnMock -> Optional.of(getTask(invocationOnMock.getArgument(0))))
                .when(taskRepoMock).findById(anyInt());
    }

    private void mockFindById_notExist() {
        Mockito.when(taskRepoMock.findById(anyInt())).thenReturn(Optional.empty());
    }

    private void mockFindAll() {
        Mockito.when(taskRepoMock.findAll(
                (Specification<Task>) null,
                getPageRequest()
        )).thenReturn(new PageImpl<>(getTaskList()));
    }

    private void mockSaveNew() {
        Mockito.doAnswer(
                invocationOnMock -> {
                    Task task = invocationOnMock.getArgument(0);
                    task.setId(TASK_ID);
                    return task;
                }).when(taskRepoMock).save(any(Task.class));
    }

    private void mockSaveUpdated() {
        Mockito.doAnswer(invocationOnMock -> invocationOnMock.getArgument(0))
                .when(taskRepoMock).save(any(Task.class));
    }


    /**
     * Вспомогательные методы.
     */

    private Task getTask(int taskId) {
        return new Task()
                .setId(taskId)
                .setTitle("Test title" + taskId)
                .setCreatedAt(CREATED_AT)
                .setAuthor(getUser(AUTHOR_ID))
                .setPerformer(getUser(PERFORMER_ID));
    }

    private List<Task> getTaskList() {
        List<Task> taskList = new ArrayList<>();
        taskList.add(getTask(11));
        taskList.add(getTask(12));
        taskList.add(getTask(13));
        return taskList;
    }

    private User getUser(int userId) {
        return new User()
                .setId(userId)
                .setEmail("test" + userId + "@gmail.com");
    }

    private PageRequest getPageRequest() {
        return PageRequest.of(PAGE_NUMBER, ITEMS_PER_PAGE, Sort.Direction.valueOf(SORT_DIR_ASC), SORT_BY);

    }

    private Map<String, String> getFilters() {
        Map<String, String> filters = new HashMap<>();
        filters.put("pageNumber", String.valueOf(PAGE_NUMBER));
        filters.put("itemsPerPage", String.valueOf(ITEMS_PER_PAGE));
        filters.put("sortBy", SORT_BY);
        filters.put("sortDir", SORT_DIR_ASC);
        filters.put("remainingKey", "remainingValue");
        return filters;
    }
}
