package uz.todoapp.web.service;

import uz.todoapp.web.model.Task;
import java.util.List;

public interface TaskService {
    Task saveTask(Task task);
    void deleteTask(Long id);
    Task getTaskById(Long id);
    List<Task> getAllTasks();
}
