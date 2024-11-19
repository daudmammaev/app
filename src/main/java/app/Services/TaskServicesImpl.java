package app.Services;

import app.Dto.DtoTask;
import app.Dto.DtoUser;
import app.models.Comment;
import app.models.Task;
import app.models.User;
import app.repository.TaskRepository;
import app.repository.UserRepository;
import app.staic.Mapper;
import app.staic.Static;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static app.staic.Mapper.*;
import static app.staic.Static.getUser;
@Service
public class TaskServicesImpl implements TaskServices{
    @Autowired
    public TaskRepository taskRepository;
    @Override
    public Task save(User user, Task task) {
        return taskRepository.save(task);
    }

    @Override
    public DtoTask getTask(long id, User user) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent() && task.get().getSendTaskTo() == user){
           return TaskToDtoTask(task.get());
        }
        return null;
    }

    @Override
    public DtoTask updateTaskForUser(Task task, User user) {
        Optional<Task> task1 = taskRepository.findById(task.getId());
        if (task1.isPresent() && task1.get().getAutor() == user){
            if(task.getStatus() != null){
                task1.get().setStatus(task.getStatus());
            }
            taskRepository.save(task1.get());
            return TaskToDtoTask(task1.get());
        };
        return null;
    }

    @Override
    public DtoTask updateTaskForAdmin(Task task, User user) {
        Optional<Task> task1 = taskRepository.findById(task.getId());
        if (task1.isPresent() && task1.get().getAutor() == user){ // Проверка автора
            if(task.getStatus() != null){
                task1.get().setStatus(task.getStatus());
            }
            if(task.getSendTaskTo() != null){
                task1.get().setSendTaskTo(task.getSendTaskTo());
            }
            if(task.getText() != null){
                task1.get().setText(task.getText());
            }
            if(task.getAutor() != null){
                task1.get().setAutor(task.getAutor());
            }
            if (task.getCommets() != null){
                task1.get().setCommets(task.getCommets());
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

    @Override
    public DtoTask addComment(long id, Comment comment, User user) {
        Optional<Task> task1 = taskRepository.findById(id);
        if (task1.isPresent()){
            task1.get().getCommets().add(comment);
            taskRepository.save(task1.get());
            return TaskToDtoTask(task1.get());
        }
        return null;
    }

    @Override
    public DtoTask deleteTask(long id) {
        Optional<Task> task1 = taskRepository.findById(id);
        if (task1.isPresent()){
            taskRepository.delete(task1.get());
            return TaskToDtoTask(task1.get());
        }
        return null;
    }

    @Override
    public List<DtoTask> getTasksByAutor(User user, Integer pageNo, Integer pageSize) {
        return taskRepository.findAllByAutor_Username(user.getUsername(), PageRequest.of(pageNo, pageSize))
                .stream().map(Mapper::TaskToDtoTask).collect(Collectors.toList());
    }

    @Override
    public List<DtoTask> getTasksByPerformer(User user, Integer pageNo, Integer pageSize) {
        return taskRepository.findAllBySendTaskTo_Username(user.getUsername(), PageRequest.of(pageNo, pageSize))
                .stream().map(Mapper::TaskToDtoTask).collect(Collectors.toList());
    }


}
