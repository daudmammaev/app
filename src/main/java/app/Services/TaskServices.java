package app.Services;

import app.Dto.DtoTask;
import app.models.Task;
import app.models.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface TaskServices {
    public Task save(User user, Task task);
    public DtoTask getTask(long id);
    public DtoTask updateTaskForUser(Task task,
                              Authentication authentication,
                              HttpServletRequest request);
    public List<DtoTask> getTasks();
}
