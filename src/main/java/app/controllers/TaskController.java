package app.controllers;

import app.Dto.DtoTask;
import app.Dto.DtoUser;
import app.Services.TaskServicesImpl;
import app.models.Comment;
import app.models.Task;
import app.models.User;
import app.repository.TaskRepository;
import app.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.slf4j.LoggerFactory;
import static app.staic.Mapper.DtotaskToTask;
import static app.staic.Mapper.TaskToDtoTask;
import static app.staic.Static.getUser;
@Slf4j
@RestController
public class TaskController {
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public TaskRepository taskRepository;
    @Autowired
    public TaskServicesImpl taskServices;

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Создать задачу", description = "Пользователь должен авторизоваться")
    @PostMapping("/create-task")
    public ResponseEntity<Task> createTask(@Valid @RequestBody DtoTask dtoTask,Authentication authentication){

        return ResponseEntity.ok().body(taskServices.save(getUser(userRepository, authentication), DtotaskToTask(dtoTask)));
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Просмотр задачи")
    @GetMapping("/check-task/{id}")
    public ResponseEntity<DtoTask> getTask(@PathVariable("id") long id){
        return ResponseEntity.ok().body(taskServices.getTask(id));
    }
    @Secured("ROLE_ADMIN")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Просмотр задач")
    @GetMapping("/check-tasks")
    public ResponseEntity<List<DtoTask>> getTasks(){
        return ResponseEntity.ok().body(taskServices.getTasks());
    }
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Обновление задачи")
    @PostMapping("/update-task/{id}")
    public ResponseEntity<Task> updateTask(
            @PathVariable("id") long id,
            @Valid @RequestBody Task task,
            Authentication authentication,
            HttpServletRequest request){


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
