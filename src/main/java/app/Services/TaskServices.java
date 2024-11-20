package app.Services;

import app.Dto.DtoTask;
import app.models.Comment;
import app.models.Task;
import app.models.User;

import java.util.List;

public interface TaskServices {
    public Task save(User user, Task task);
    public DtoTask getTask(long id, User user);
    public DtoTask updateTaskForUser(Task task, User user);
    public DtoTask updateTaskForAdmin(Task task, User user);
    public List<DtoTask> getTasks();
    public DtoTask addComment(long id, Comment comment, User user);
    public DtoTask deleteTask(long id);
    public List<DtoTask> getTasksByAutor(User user, Integer pageNo, Integer pageSize);
    public List<DtoTask> getTasksByPerformer(User user, Integer pageNo, Integer pageSize);
}
