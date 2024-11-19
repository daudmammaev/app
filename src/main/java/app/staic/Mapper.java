package app.staic;

import app.Dto.DtoTask;
import app.Dto.DtoUser;
import app.models.Task;
import app.models.User;

public class Mapper {
    public static DtoTask TaskToDtoTask(Task task){
        DtoTask dtoTask = new DtoTask();
        dtoTask.setText(task.getText());
        dtoTask.setStatus(task.getStatus());
        dtoTask.setCommets(task.getCommets());
        dtoTask.setSendTaskTo(UserToDtouser(task.getSendTaskTo()));
        dtoTask.setAutor(UserToDtouser(task.getAutor()));
        return dtoTask;
    }
    public static Task DtotaskToTask(DtoTask dtoTask){
        Task task = new Task();
        task.setText(dtoTask.getText());
        task.setStatus(dtoTask.getStatus());
        task.setCommets(dtoTask.getCommets());
        task.setSendTaskTo(DtouserToUser(dtoTask.getSendTaskTo()));
        task.setAutor(DtouserToUser(dtoTask.getAutor()));
        return task;
    }
    public static DtoUser UserToDtouser(User user){
        DtoUser dtoUser = new DtoUser();
        dtoUser.setUsername(user.getUsername());
        dtoUser.setEmail(user.getEmail());
        return dtoUser;
    }
    public static User DtouserToUser(DtoUser dtoUser){
        User user = new User();
        user.setUsername(dtoUser.getUsername());
        user.setEmail(dtoUser.getEmail());
        return user;
    }
}
