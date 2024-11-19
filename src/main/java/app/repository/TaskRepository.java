package app.repository;

import app.models.Task;
import app.models.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    public List<Task> findAllByAutor_Username(String string, Pageable pageable);
    public List<Task> findAllBySendTaskTo_Username(String string, Pageable pageable);
}
