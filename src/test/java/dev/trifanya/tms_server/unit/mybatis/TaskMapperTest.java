/*
package dev.trifanya.taskmanagementsystem.unit.mybatis;

import dev.trifanya.taskmanagementsystem.TaskMapper;
import dev.trifanya.taskmanagementsystem.model.task.Task;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskMapperTest {

    private static final long TASK_ID = 1;

    @Autowired
    private TaskMapper taskMapper;

    @Test
    public void getTask_shouldReturnTaskWithGivenId() {
        Task task = taskMapper.getTask(TASK_ID);

        Assertions.assertNotNull(task);
        Assertions.assertEquals(task.getId(), TASK_ID);
    }
}
*/
