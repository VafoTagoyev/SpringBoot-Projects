package uz.todoapp.web.repository;

import uz.todoapp.web.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // Custom query methods for Task can be defined here
}


