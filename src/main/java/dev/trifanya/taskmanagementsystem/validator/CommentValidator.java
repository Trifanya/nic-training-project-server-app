package dev.trifanya.taskmanagementsystem.validator;

import org.springframework.stereotype.Component;
import dev.trifanya.taskmanagementsystem.model.User;
import dev.trifanya.taskmanagementsystem.model.task.Task;
import dev.trifanya.taskmanagementsystem.exception.UnavailableActionException;

@Component
public class CommentValidator {
    public void validateNewComment(Task task, User commentAuthor/*int commentAuthorId*/) {
        System.out.println(task.getAuthor().equals(commentAuthor));
        System.out.println(task.getPerformer().equals(commentAuthor));
        if (!task.getAuthor().equals(commentAuthor) && !task.getPerformer().equals(commentAuthor)) {
            throw new UnavailableActionException("Комментарий к задаче может опубликовать только ее автор или исполнитель.");
        }
    }

    public void validateUpdatedComment(User commentAuthor, User commentModifier) {
        if (!commentAuthor.equals(commentModifier)) {
            throw new UnavailableActionException("Комментарий к задаче может редактировать только автор комментария.");
        }
    }

    public void validateDelete(User commentAuthor, User commentDeleter) {
        if (!commentAuthor.equals(commentDeleter)) {
            throw new UnavailableActionException("Комментарий может удалить только автор комментария.");
        }
    }
}
