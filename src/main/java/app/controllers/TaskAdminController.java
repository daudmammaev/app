package app.controllers;

import app.Dto.DtoTask;
import app.Services.TaskServicesImpl;
import app.models.Task;
import app.models.User;
import app.repository.TaskRepository;
import app.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static app.staic.Mapper.DtotaskToTask;
import static app.staic.Static.getUser;

@RestController
public class TaskAdminController {
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public TaskRepository taskRepository;
    @Autowired
    public TaskServicesImpl taskServices;
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Создать задачу")
    @PostMapping("/create-task")
    public ResponseEntity<Task> createTask(@Valid @RequestBody DtoTask dtoTask, Authentication authentication){
        return ResponseEntity.ok().body(taskServices.save(getUser(userRepository, authentication), DtotaskToTask(dtoTask)));
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Удаление задачи")
    @PostMapping("/delete-task/{id}")
    public ResponseEntity<DtoTask> deleteTask(@PathVariable("id") long id){
        return ResponseEntity.ok(taskServices.deleteTask(id));
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Обновление задачи")
    @PostMapping("/update-task/admin")
    public ResponseEntity<DtoTask> updateTaskForAdmin(@Valid @RequestBody Task task, Authentication authentication){
        return ResponseEntity.ok().body(taskServices.updateTaskForUser(task, getUser(userRepository, authentication)));
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Получение списка задач по имени")
    @PostMapping("/get-tasks-autor")
    public ResponseEntity<List<DtoTask>> getAllTaskByAutor(@RequestBody User user,
                                                           @RequestParam(defaultValue = "0") Integer pageNo,
                                                           @RequestParam(defaultValue = "10") Integer pageSize){
        return ResponseEntity.ok().body(taskServices.getTasksByAutor(user, pageNo, pageSize));
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Получение списка задач по исполнителю")
    @PostMapping("/get-tasks-performer")
    public ResponseEntity<List<DtoTask>> getAllTaskByPerformer(@RequestBody User user,
                                                               @RequestParam(defaultValue = "0") Integer pageNo,
                                                               @RequestParam(defaultValue = "10") Integer pageSize){
        return ResponseEntity.ok().body(taskServices.getTasksByPerformer(user, pageNo, pageSize));
    }
}
