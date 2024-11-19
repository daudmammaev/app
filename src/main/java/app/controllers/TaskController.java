package app.controllers;

import app.Dto.DtoTask;
import app.Services.TaskServicesImpl;
import app.models.Comment;
import app.models.Task;
import app.repository.TaskRepository;
import app.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static app.staic.Static.getUser;

@RestController
public class TaskController {
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public TaskRepository taskRepository;
    @Autowired
    public TaskServicesImpl taskServices;

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Просмотр задачи")
    @GetMapping("/check-task/{id}")
    public ResponseEntity<DtoTask> getTask(@PathVariable("id") long id, Authentication authentication){
        return ResponseEntity.ok().body(taskServices.getTask(id, getUser(userRepository, authentication)));
    }
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Просмотр задач")
    @GetMapping("/check-tasks")
    public ResponseEntity<List<DtoTask>> getTasks(){
        return ResponseEntity.ok().body(taskServices.getTasks());
    }
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Обновление задачи")
    @PostMapping("/update-task")
    public ResponseEntity<DtoTask> updateTaskForUser(@Valid @RequestBody Task task, Authentication authentication){
        return ResponseEntity.ok().body(taskServices.updateTaskForUser(task, getUser(userRepository, authentication)));
    }
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Добавления комментария к задаче")
    @PostMapping("/task/{id}/comment")
    public ResponseEntity<DtoTask> addComment(@PathVariable("id") long id,
                                              @Valid @RequestBody Comment comment,
                                              Authentication authentication){
        return ResponseEntity.ok().body(taskServices.addComment(id, comment, getUser(userRepository, authentication)));
    }
}
