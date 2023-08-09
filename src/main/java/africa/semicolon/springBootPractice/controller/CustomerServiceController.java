package africa.semicolon.springBootPractice.controller;

import africa.semicolon.springBootPractice.dtos.requests.*;
import africa.semicolon.springBootPractice.dtos.response.*;
import africa.semicolon.springBootPractice.exceptions.CustomerDoesNotExist;
import africa.semicolon.springBootPractice.exceptions.DuplicateCustomerException;
import africa.semicolon.springBootPractice.exceptions.InvalidEmailException;
import africa.semicolon.springBootPractice.exceptions.InvalidPhoneNumber;
import africa.semicolon.springBootPractice.models.Task;
import africa.semicolon.springBootPractice.services.CustomerServiceImpl;
import africa.semicolon.springBootPractice.services.CustomerServiceInterface;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.ObjDoubleConsumer;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1")
@CrossOrigin(origins = "*")
public class CustomerServiceController {
    private final CustomerServiceInterface customerService;

    @PostMapping("/user")
    public Object CreateNewUser(@RequestBody RegistrationRequest registrationRequest) throws DuplicateCustomerException, InvalidPhoneNumber, InvalidEmailException {
        RegistrationResponse registeredCustomer = customerService.registerCustomer(registrationRequest);
        return registeredCustomer;
    }
    @PostMapping("/login")
    public Object loginCustomer(@RequestBody LogInRequest logInRequest) throws CustomerDoesNotExist {
        LoggedInCustomerResponse loggedInCustomer = customerService.logIn(logInRequest);
        return loggedInCustomer;
    }
    @PutMapping("/{email}")
    public Object updateCustomer(@PathVariable String email, @RequestBody UpdateRequest updateRequest) throws InvalidPhoneNumber, CustomerDoesNotExist {
        UpdatedCustomerResponse updatedCustomer = customerService.updateCustomer(email, updateRequest);
        return updatedCustomer;
    }
    @DeleteMapping("/deleteCustomer")
    public DeleteResponse deleteCustomer(@RequestBody DeleteRequest deleteRequest) throws CustomerDoesNotExist {
        var customerDeleteResponse =  customerService.deleteCustomer(deleteRequest);
        return  customerDeleteResponse;}

    @GetMapping ("/tasks")
    public Object getTaskAll(@RequestBody LogInRequest logInRequest) throws CustomerDoesNotExist {
        List<Task> allTasks = customerService.fetchTask(logInRequest);
        return allTasks;
    }








































//    @PostMapping("/task/{email}")
//    public Object createTask(@PathVariable String email ,@RequestBody TaskRequest taskRequest) throws CustomerDoesNotExist {
//        TaskResponse createdTask = customerService.makeTask(email,taskRequest);
//        return createdTask;
//    }
//    @PostMapping ("/tasks")
//    public Object getTaskAll(@RequestBody LogInRequest logInRequest) throws CustomerDoesNotExist {
//        List<Task> allTasks = customerService.fetchTask(logInRequest);
//        return allTasks;
//    }
//    @PostMapping("/deleteAll")
//    public DeleteResponse deleteAllTasks(@RequestBody LogInRequest logInRequest) throws CustomerDoesNotExist {
//        var deletedTasks = customerService.deleteAllTask(logInRequest);
//       return deletedTasks;
//    }
//    @GetMapping  ("/singleDelete")
//    public DeleteResponse deleteTaskByTitle(@RequestBody DeleteRequest deleteRequest) throws CustomerDoesNotExist {
//        var deletedTaskResponse = customerService.deleteTaskByTitle(deleteRequest);
//        return deletedTaskResponse;
//    }
//    @GetMapping("/fetchtask")
//    public TaskResponse getTaskByTitle(@RequestBody FetchRequest fetchRequest) throws CustomerDoesNotExist {
//        var fetchedTask = customerService.fetchTaskByTitle(fetchRequest);
//        return fetchedTask;
//    }
//    @DeleteMapping("/deleteCustomer")
//    public DeleteResponse deleteCustomer(@RequestBody DeleteRequest deleteRequest) throws CustomerDoesNotExist {
//       var customerDeleteResponse =  customerService.deleteCustomer(deleteRequest);
//       return  customerDeleteResponse;
//    }
//    @PutMapping("/updateTask/{title}")
//    public UpdatedTaskResponse updateTask(@PathVariable String title, @RequestBody UpdateTaskRequest updateTaskRequest) throws CustomerDoesNotExist {
//        var updateTaskResponse = customerService.updateTask(title, updateTaskRequest);
//        return updateTaskResponse;
//    }
}
