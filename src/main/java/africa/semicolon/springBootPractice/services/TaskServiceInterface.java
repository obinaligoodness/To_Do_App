package africa.semicolon.springBootPractice.services;

import africa.semicolon.springBootPractice.dtos.requests.*;
import africa.semicolon.springBootPractice.dtos.response.DeleteResponse;
import africa.semicolon.springBootPractice.dtos.response.TaskResponse;
import africa.semicolon.springBootPractice.dtos.response.UpdatedTaskResponse;
import africa.semicolon.springBootPractice.exceptions.CustomerDoesNotExist;
import africa.semicolon.springBootPractice.models.Task;

import java.util.List;

public interface TaskServiceInterface {
    TaskResponse makeTask(String email, TaskRequest taskRequest) throws CustomerDoesNotExist;

    List<Task> fetchTask(LogInRequest logInRequest) throws CustomerDoesNotExist;

    DeleteResponse deleteAllTask(LogInRequest logInRequest) throws CustomerDoesNotExist;

    DeleteResponse deleteTaskByTitle(DeleteRequest deleteRequest) throws CustomerDoesNotExist;

    TaskResponse fetchTaskByTitle (FetchRequest fetchRequest) throws CustomerDoesNotExist;

    UpdatedTaskResponse updateTask (String title, UpdateTaskRequest updateTaskRequest) throws CustomerDoesNotExist;
}
