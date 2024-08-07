package app.controllers;

import app.Dto.DtoTask;
import app.Dto.DtoUser;
import app.models.Comment;
import app.models.Task;
import app.models.User;
import app.repository.TaskRepository;
import app.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@Tag(name = "Основной контроллер")
@RestController
public class TaskController {
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public TaskRepository taskRepository;

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Создать задачу", description = "Пользователь должен авторизоваться")
    @PostMapping("/create-task")
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task,
                           @Parameter(description = "Нужно, чтобы ловить пользователя") Authentication authentication, HttpServletRequest request){
        authentication = (Authentication) request.getUserPrincipal();
        // Получаем информацию о пользователе
        var userDetails = (UserDetails) authentication.getPrincipal();
        // Используем
        Optional<User> user = userRepository.findByPassword(userDetails.getPassword());

        if(user.isPresent()){
            task.setUser(user.get());
            taskRepository.save(task);
        }

        return ResponseEntity.status(201).body(task);
    }
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Просмотр задачи")
    @GetMapping("/check-task/{id}")
    public ResponseEntity<DtoTask> getTask(@PathVariable("id") long id){
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()){
            DtoTask dtoTask = new DtoTask();
            DtoUser dtoUser = new DtoUser();
            dtoUser.setEmail(task.get().getUser().getEmail());
            dtoUser.setUsername(task.get().getUser().getUsername());
            dtoTask.setDtoUser(dtoUser);
            dtoTask.setId(task.get().getId());
            dtoTask.setText(task.get().getText());
            dtoTask.setCommets(task.get().getCommets());
            return ResponseEntity.ok().body(dtoTask);
        }
        return null;
    }
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Обновление задачи")
    @PostMapping("/update-task/{id}")
    public ResponseEntity<Task> updateTask(
            @PathVariable("id") long id,
            @Valid @RequestBody Task task,
            Authentication authentication,
            HttpServletRequest request){

        Optional<Task> task1 = taskRepository.findById(id);
        authentication = (Authentication) request.getUserPrincipal();
        // Получаем информацию о пользователе
        var userDetails = (UserDetails) authentication.getPrincipal();
        // Используем
        Optional<User> user = userRepository.findByPassword(userDetails.getPassword());

        if (task1.isPresent() && task1.get().getUser() == user.get()){

            if(task.getText() != null){
                task1.get().setText(task.getText());
            }
            if(task.getStatus() != null){
                task1.get().setStatus(task.getStatus());
            }
            if(task.getSendTaskTo() != null && userRepository.findByUsername(task.getSendTaskTo()).isPresent()){
                task1.get().setUser(userRepository.findByUsername(task.getSendTaskTo()).get());
            }
            return ResponseEntity.ok(taskRepository.save(task1.get()));
        }
        return ResponseEntity.status(201).body(task);
    }
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Удаление задачи")
    @PostMapping("/delete-task/{id}")
    public ResponseEntity<Task> deleteTask(@PathVariable("id") long id){
        Optional<Task> task1 = taskRepository.findById(id);
        taskRepository.delete(task1.get());
        return ResponseEntity.ok(task1.get());
    }
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Добавления к задачи комментария")
    @PostMapping("/task/{id}/comment")
    public ResponseEntity<Task> addComment(@PathVariable("id") long id, @Valid @RequestBody Comment comment){
        Optional<Task> task1 = taskRepository.findById(id);
        if (task1.isPresent()){
            task1.get().getCommets().add(comment);

            taskRepository.save(task1.get());
            return ResponseEntity.ok(task1.get());
        }
        return null;
    }
}
