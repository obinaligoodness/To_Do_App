package africa.semicolon.springBootPractice.controller;

import africa.semicolon.springBootPractice.dtos.requests.*;
import africa.semicolon.springBootPractice.dtos.response.DeleteResponse;
import africa.semicolon.springBootPractice.dtos.response.TaskResponse;
import africa.semicolon.springBootPractice.dtos.response.UpdatedTaskResponse;
import africa.semicolon.springBootPractice.exceptions.CustomerDoesNotExist;
import africa.semicolon.springBootPractice.models.Task;
import africa.semicolon.springBootPractice.services.TaskServiceInterface;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/task")
@CrossOrigin(origins = "*")
public class TaskServiceController {
    private final TaskServiceInterface taskServiceInterface;

    @PostMapping("/task/{email}")
    public Object createTask(@PathVariable String email , @RequestBody TaskRequest taskRequest) throws CustomerDoesNotExist {
        TaskResponse createdTask = taskServiceInterface.makeTask(email,taskRequest);
        return createdTask;
    }
    @PostMapping ("/tasks")
    public Object getTaskAll(@RequestBody LogInRequest logInRequest) throws CustomerDoesNotExist {
        List<Task> allTasks = taskServiceInterface.fetchTask(logInRequest);
        return allTasks;
    }
    @DeleteMapping("/deleteAll")
    public DeleteResponse deleteAllTasks(@RequestBody LogInRequest logInRequest) throws CustomerDoesNotExist {
        var deletedTasks = taskServiceInterface.deleteAllTask(logInRequest);
       return deletedTasks;
    }
    @DeleteMapping  ("/singleDelete")
    public DeleteResponse deleteTaskByTitle(@RequestBody DeleteRequest deleteRequest) throws CustomerDoesNotExist {
        var deletedTaskResponse = taskServiceInterface.deleteTaskByTitle(deleteRequest);
        return deletedTaskResponse;
    }
    @GetMapping("/fetchtask")
    public TaskResponse getTaskByTitle(@RequestBody FetchRequest fetchRequest) throws CustomerDoesNotExist {
        var fetchedTask = taskServiceInterface.fetchTaskByTitle(fetchRequest);
        return fetchedTask;
    }

    @PutMapping("/updateTask/{title}")
    public UpdatedTaskResponse updateTask(@PathVariable String title, @RequestBody UpdateTaskRequest updateTaskRequest) throws CustomerDoesNotExist {
        var updateTaskResponse = taskServiceInterface.updateTask(title, updateTaskRequest);
        return updateTaskResponse;
    }

}
