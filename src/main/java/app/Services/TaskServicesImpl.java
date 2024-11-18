package app.Services;

import app.Dto.DtoTask;
import app.Dto.DtoUser;
import app.models.Task;
import app.models.User;
import app.repository.TaskRepository;
import app.repository.UserRepository;
import app.staic.Mapper;
import app.staic.Static;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static app.staic.Mapper.TaskToDtoTask;
import static app.staic.Static.getUser;
@Service
public class TaskServicesImpl implements TaskServices{
    public UserRepository userRepository;
    @Autowired
    public TaskRepository taskRepository;
    @Override
    public Task save(User user, Task task) {
        task.setAutor(user);

        return taskRepository.save(task);
    }

    @Override
    public DtoTask getTask(long id) {
        Optional<Task> task = taskRepository.findById(id);
        return task.map(Mapper::TaskToDtoTask).orElse(null);
    }

    @Override
    public DtoTask updateTaskForUser(Task task, Authentication authentication, HttpServletRequest request) {
        Optional<Task> task1 = taskRepository.findById(task.getId());
        authentication = (Authentication) request.getUserPrincipal();
        var userDetails = (UserDetails) authentication.getPrincipal();
        Optional<User> user = userRepository.findByPassword(userDetails.getPassword());

        if (task1.isPresent() && task1.get().getAutor() == user.get()){

            if(task.getText() != null){
                task1.get().setText(task.getText());
            }
            if(task.getStatus() != null){
                task1.get().setStatus(task.getStatus());
            }
            if(task.getSendTaskTo() != null
                    && userRepository.findByUsername(task.getSendTaskTo().getUsername()).isPresent()){
                task1.get().setAutor(userRepository.findByUsername(task.getSendTaskTo().getUsername()).get());
            }
            taskRepository.save(task1.get());
            return TaskToDtoTask(task1.get());
        };
        return null;
    }

    @Override
    public List<DtoTask> getTasks() {
        return taskRepository.findAll().stream().map(Mapper::TaskToDtoTask).collect(Collectors.toList());
    }


}
