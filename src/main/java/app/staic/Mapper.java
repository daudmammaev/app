package app.staic;

import app.Dto.DtoTask;
import app.models.Task;

public class Mapper {
    public static DtoTask TaskToDtoTask(Task task){
        DtoTask dtoTask = new DtoTask();
        dtoTask.setText(task.getText());
        dtoTask.setStatus(task.getStatus());
        dtoTask.setCommets(task.getCommets());
        dtoTask.setSendTaskTo(task.getSendTaskTo());
        dtoTask.setAutor(task.getAutor());
        return dtoTask;
    }
    public static Task DtotaskToTask(DtoTask dtoTask){
        Task task = new Task();
        dtoTask.setText(dtoTask.getText());
        dtoTask.setStatus(dtoTask.getStatus());
        dtoTask.setCommets(dtoTask.getCommets());
        dtoTask.setSendTaskTo(dtoTask.getSendTaskTo());
        dtoTask.setAutor(dtoTask.getAutor());
        return task;
    }
}
