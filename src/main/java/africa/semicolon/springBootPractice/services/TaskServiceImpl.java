package africa.semicolon.springBootPractice.services;

import africa.semicolon.springBootPractice.dtos.requests.*;
import africa.semicolon.springBootPractice.dtos.response.DeleteResponse;
import africa.semicolon.springBootPractice.dtos.response.TaskResponse;
import africa.semicolon.springBootPractice.dtos.response.UpdatedTaskResponse;
import africa.semicolon.springBootPractice.exceptions.CustomerDoesNotExist;
import africa.semicolon.springBootPractice.models.Customer;
import africa.semicolon.springBootPractice.models.Task;
import africa.semicolon.springBootPractice.repositories.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@AllArgsConstructor
@Service
public class TaskServiceImpl implements TaskServiceInterface{
    private final TaskRepository taskRepository;
    private final CustomerServiceInterface customerServiceInterface;

    private Customer findCustomerByEmail(String email) {
        return customerServiceInterface.findCustomerByEmail(email);
    }
    @Override
    public TaskResponse makeTask(String email, TaskRequest taskRequest) throws CustomerDoesNotExist {
        Customer foundCustomer = findCustomerByEmail(email);
        if (foundCustomer==null) throw new CustomerDoesNotExist("No such customer exists");
        Task task = new Task();
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setStatus(taskRequest.getStatus());
        task.setCustomer(foundCustomer);
        foundCustomer.getTasks().add(task);
        taskRepository.save(task);
        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setTitle(task.getTitle());
        taskResponse.setDescription(task.getDescription());
        taskResponse.setStatus(task.getStatus());
        return taskResponse;
    }

    @Override
    public List<Task> fetchTask(LogInRequest logInRequest) throws CustomerDoesNotExist {
        Customer foundCustomer =customerServiceInterface.findCustomerByEmail(logInRequest.getEmail());
        if (foundCustomer==null) throw new CustomerDoesNotExist("No such customer exist, please try passing in the correct email");
        var foundCustomerId = foundCustomer.getId();
        var allTask = taskRepository.findByCustomer_id(foundCustomerId);
        return allTask;
    }

    @Override
    public DeleteResponse deleteAllTask(LogInRequest logInRequest) throws CustomerDoesNotExist {
        List<Task> allTask = fetchTask(logInRequest);
        for (Task task:allTask){
            task.setCustomer(null);
        }
        taskRepository.deleteAll();
        DeleteResponse deleteResponse = new DeleteResponse();
        deleteResponse.setMessage("delete successful");
        return deleteResponse;
    }

    @Override
    public DeleteResponse deleteTaskByTitle(DeleteRequest deleteRequest) throws CustomerDoesNotExist {
        Customer foundCustomer =customerServiceInterface.findCustomerByEmail(deleteRequest.getEmail());
        if (foundCustomer==null) throw new CustomerDoesNotExist("No such customer exist, please try a correct email");
        if (foundCustomer.getEmail().equals(deleteRequest.getEmail())){
            taskRepository.delete(taskRepository.findTaskByTitle(deleteRequest.getTitle()));
        }
        DeleteResponse deleteResponse = new DeleteResponse();
        deleteResponse.setMessage("delete Successful");
        return deleteResponse;
    }

    @Override
    public TaskResponse fetchTaskByTitle(FetchRequest fetchRequest) throws CustomerDoesNotExist {
        TaskResponse taskResponse = new TaskResponse();
        Customer foundCustomer = customerServiceInterface.findCustomerByEmail(fetchRequest.getEmail());
        if (foundCustomer==null) {throw new CustomerDoesNotExist("No such customer exist, please try a correct email");}
        Task foundTask = taskRepository.findTaskByTitle(fetchRequest.getTitle());
        taskResponse.setTitle(foundTask.getTitle());
        taskResponse.setDescription(foundTask.getDescription());
        taskResponse.setStatus(foundTask.getStatus());
        return taskResponse;
    }

    @Override
    public UpdatedTaskResponse updateTask(String title, UpdateTaskRequest updateTaskRequest) throws CustomerDoesNotExist {
        UpdatedTaskResponse updatedTaskResponse = new UpdatedTaskResponse();
        Customer foundCustomer = customerServiceInterface.findCustomerByEmail(updateTaskRequest.getEmail());
        List<Task> listOfCustomerTask = taskRepository.findByCustomer_id(foundCustomer.getId());
        for (Task foundTask:listOfCustomerTask){
            if (foundTask.getTitle().equals(title)){
                if (updateTaskRequest.getTitle()!=null){foundTask.setTitle(updateTaskRequest.getTitle());}else {foundTask.setTitle(foundTask.getTitle());}
                if (updateTaskRequest.getDescription()!=null){foundTask.setDescription(updateTaskRequest.getDescription());}else {foundTask.setDescription(foundTask.getDescription());}
                if (updateTaskRequest.getDescription()!=null){foundTask.setStatus(updateTaskRequest.getStatus());}else {foundTask.setStatus(foundTask.getStatus());}
                taskRepository.save(foundTask);
                updatedTaskResponse.setMessage("you have successfully updated the task");
            } else {
                throw new CustomerDoesNotExist("");
            }
        }
        return updatedTaskResponse;
    }
}
