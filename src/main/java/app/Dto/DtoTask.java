package app.Dto;

import app.models.Comment;
import app.models.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
@Data
public class DtoTask {
    private Long id;
    private String text;
    private String status;
    private DtoUser dtoUser;
    private String sendTaskTo;
    private List<Comment> commets;

}
