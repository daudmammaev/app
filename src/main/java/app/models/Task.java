package app.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(max = 100)
    private String text;
    private String status;
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    private User autor;
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    private User sendTaskTo;
    @OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    private List<Comment> commets;

}
