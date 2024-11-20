package app.Dto;

import app.models.Comment;
import lombok.Data;

import java.util.List;
@Data
public class DtoTask {
    private String text;
    private String status;
    private DtoUser autor;
    private DtoUser sendTaskTo;
    private List<Comment> commets;

}
