package dev.trifanya.tms_server.unit.service;

import dev.trifanya.tms_server.model.User;
import dev.trifanya.tms_server.model.Comment;
import dev.trifanya.tms_server.model.task.Task;
import dev.trifanya.tms_server.service.CommentService;
import dev.trifanya.tms_server.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    private static final int COMMENT_ID = 1;
    private static final int TASK_ID = 1;
    private static final int AUTHOR_ID = 1;
    private static final LocalDateTime CREATED_AT = LocalDateTime.parse("2023-12-10T15:52:00");

    @Mock
    private CommentRepository commentRepoMock;

    @InjectMocks
    private CommentService testingService;


    @Test
    public void getComment_commentIsExist_shouldReturnComment() {
        // Given
        mockFindById_exist();
        // When
        Comment resultComment = testingService.getComment(COMMENT_ID);
        // Then
        Mockito.verify(commentRepoMock).findById(COMMENT_ID);
        Assertions.assertEquals(getComment(COMMENT_ID), resultComment);
    }

    @Test
    public void getComment_commentIsNotExist_shouldThrowException() {
        // Given
        mockFindById_notExist();
        // When // Then
        Assertions.assertThrows(
                NotFoundException.class,
                () -> testingService.getComment(COMMENT_ID)
        );
    }

    @Test
    public void getComments_shouldReturnCommentList() {
        // Given
        mockFindAllByTaskId();
        // When
        List<Comment> resultList = testingService.getComments(TASK_ID);
        // Then
        Mockito.verify(commentRepoMock).findAllByTaskId(TASK_ID);
        Assertions.assertIterableEquals(getCommentList(), resultList);
    }

    @Test
    public void createNewComment_shouldAssignDateTimeAndTaskAndAuthorAndId() {
        // Given
        mockSaveNew();
        Comment commentToSave = new Comment();
        Task task = getTask(TASK_ID);
        User author = getUser(AUTHOR_ID);
        // When
        Comment resultComment = testingService.createNewComment(commentToSave, task, author);
        // Then
        Mockito.verify(commentRepoMock).save(commentToSave);
        Assertions.assertNotNull(resultComment.getId());
        Assertions.assertNotNull(resultComment.getCreatedAt());
        Assertions.assertEquals(task, resultComment.getTask());
        Assertions.assertEquals(author, resultComment.getAuthor());
    }

    @Test
    public void updateCommentInfo_shouldNotModifyIdAndDateTimeAndTaskAndAuthor() {
        // Given
        String testText = "Test text";
        Comment updatedComment = new Comment().setId(COMMENT_ID).setText(testText);
        mockFindById_exist();
        mockSaveUpdated();
        // When
        Comment resultComment = testingService.updateCommentInfo(updatedComment);
        // Then
        Mockito.verify(commentRepoMock).findById(COMMENT_ID);
        Mockito.verify(commentRepoMock).save(updatedComment);
        Assertions.assertEquals(COMMENT_ID, resultComment.getId());
        Assertions.assertEquals(CREATED_AT, resultComment.getCreatedAt());
        Assertions.assertEquals(getTask(TASK_ID), resultComment.getTask());
        Assertions.assertEquals(getUser(AUTHOR_ID), resultComment.getAuthor());
    }

    @Test
    public void deleteComment() {
        // When
        testingService.deleteComment(COMMENT_ID);
        // Then
        Mockito.verify(commentRepoMock).deleteById(COMMENT_ID);
    }


    /**
     * Определение поведения mock-объектов.
     */

    private void mockFindById_exist() {
        Mockito.doAnswer(invocationOnMock -> Optional.of(
                getComment(invocationOnMock.getArgument(0))
        )).when(commentRepoMock).findById(anyInt());
    }

    private void mockFindById_notExist() {
        Mockito.when(commentRepoMock.findById(anyInt()))
                .thenReturn(Optional.empty());
    }

    private void mockFindAllByTaskId() {
        Mockito.when(commentRepoMock.findAllByTaskId(anyInt()))
                .thenReturn(getCommentList());
    }

    private void mockSaveNew() {
        Mockito.doAnswer(
                invocationOnMock -> {
                    Comment comment = invocationOnMock.getArgument(0);
                    return comment.setId(COMMENT_ID);
                }).when(commentRepoMock).save(any(Comment.class));
    }

    private void mockSaveUpdated() {
        Mockito.doAnswer(invocationOnMock -> invocationOnMock.getArgument(0))
                .when(commentRepoMock).save(any(Comment.class));
    }


    /**
     * Вспомогательные методы.
     */

    private Comment getComment(int commentId) {
        return new Comment()
                .setId(commentId)
                .setCreatedAt(CREATED_AT)
                .setTask(getTask(TASK_ID))
                .setAuthor(getUser(AUTHOR_ID));
    }

    private Task getTask(int taskId) {
        return new Task().setId(taskId);
    }

    private User getUser(int userId) {
        return new User().setId(userId);
    }

    private List<Comment> getCommentList() {
        List<Comment> commentList = new ArrayList<>();
        commentList.add(getComment(11));
        commentList.add(getComment(12));
        commentList.add(getComment(13));
        return commentList;
    }
}
