package africa.semicolon.springBootPractice.services;

import africa.semicolon.springBootPractice.dtos.requests.*;
import africa.semicolon.springBootPractice.dtos.response.*;
import africa.semicolon.springBootPractice.exceptions.CustomerDoesNotExist;
import africa.semicolon.springBootPractice.exceptions.DuplicateCustomerException;
import africa.semicolon.springBootPractice.exceptions.InvalidEmailException;
import africa.semicolon.springBootPractice.exceptions.InvalidPhoneNumber;
import africa.semicolon.springBootPractice.models.Customer;
import africa.semicolon.springBootPractice.models.Task;

import java.util.List;

public interface CustomerServiceInterface {
    RegistrationResponse registerCustomer(RegistrationRequest registrationRequest) throws DuplicateCustomerException, InvalidPhoneNumber, InvalidEmailException;
//    Customer findCustomerByEmail(String email);
    DeleteResponse deleteCustomer(DeleteRequest deleteRequest) throws CustomerDoesNotExist;
    LoggedInCustomerResponse logIn(LogInRequest logInRequest) throws CustomerDoesNotExist;
    public Customer findCustomerByEmail(String email);

    UpdatedCustomerResponse updateCustomer(String email,UpdateRequest updateRequest) throws InvalidPhoneNumber, CustomerDoesNotExist;

//    TaskResponse makeTask(String email,TaskRequest taskRequest) throws CustomerDoesNotExist;
//
    List<Task> fetchTask(LogInRequest logInRequest) throws CustomerDoesNotExist;
//
//    DeleteResponse deleteAllTask(LogInRequest logInRequest) throws CustomerDoesNotExist;
//
//    DeleteResponse deleteTaskByTitle(DeleteRequest deleteRequest) throws CustomerDoesNotExist;
//
//    TaskResponse fetchTaskByTitle (FetchRequest fetchRequest) throws CustomerDoesNotExist;
//
//    UpdatedTaskResponse updateTask (String title, UpdateTaskRequest updateTaskRequest) throws CustomerDoesNotExist;
}
