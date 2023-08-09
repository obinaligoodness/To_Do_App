package africa.semicolon.springBootPractice.services;

import africa.semicolon.springBootPractice.dtos.requests.*;
import africa.semicolon.springBootPractice.dtos.response.*;
import africa.semicolon.springBootPractice.exceptions.CustomerDoesNotExist;
import africa.semicolon.springBootPractice.exceptions.DuplicateCustomerException;
import africa.semicolon.springBootPractice.exceptions.InvalidEmailException;
import africa.semicolon.springBootPractice.exceptions.InvalidPhoneNumber;
import africa.semicolon.springBootPractice.models.Customer;
import africa.semicolon.springBootPractice.models.Task;
import africa.semicolon.springBootPractice.repositories.CustomerRepository;
import africa.semicolon.springBootPractice.repositories.TaskRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerServiceInterface{
    private final CustomerRepository customerRepository;
    private final TaskRepository taskRepository;
    @Override
    public RegistrationResponse registerCustomer(RegistrationRequest registrationRequest) throws DuplicateCustomerException, InvalidPhoneNumber, InvalidEmailException {
        Customer customer = new Customer();
        customer.setFirstName(registrationRequest.getFirstName());
        customer.setLastName(registrationRequest.getLastName());
//        if (registrationRequest.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) throw new InvalidEmailException("Invalid Email, please pass in a valid email address");
        customer.setEmail(registrationRequest.getEmail());
        if (registrationRequest.getPhoneNumber().length()!=11) throw new InvalidPhoneNumber("Please enter a correct phone number");
        customer.setPhoneNumber(registrationRequest.getPhoneNumber());
        customer.setPassword(registrationRequest.getPassword());
        if (findCustomerByEmail(registrationRequest.getEmail()) !=null)throw new DuplicateCustomerException("Customer already exists");
        else{customerRepository.save(customer);
        RegistrationResponse registrationResponse = new RegistrationResponse();
        registrationResponse.setMessage("You have successfully registered");
        return registrationResponse;}
    }

    @Override
    public DeleteResponse deleteCustomer(DeleteRequest deleteRequest) throws CustomerDoesNotExist {
        Customer foundCustomer = customerRepository.findCustomerByEmail(deleteRequest.getEmail());
        if (foundCustomer==null) throw new CustomerDoesNotExist("this account does not exist");
        customerRepository.delete(foundCustomer);
        DeleteResponse deleteResponse = new DeleteResponse();
        deleteResponse.setMessage("you have successfully deleted your account");
        return deleteResponse;
    }

    public Customer findCustomerByEmail(String email) {
        return customerRepository.findCustomerByEmail(email);
    }

    @Override
    public LoggedInCustomerResponse logIn(LogInRequest logInRequest) throws CustomerDoesNotExist {
        Customer foundCustomer = findCustomerByEmail(logInRequest.getEmail());
        if (foundCustomer==null) throw new CustomerDoesNotExist("You have to register first");
        LoggedInCustomerResponse loggedInCustomerResponse = new LoggedInCustomerResponse();
        if (foundCustomer.getPassword().equals(logInRequest.getPassword())){
        loggedInCustomerResponse.setFirstName(foundCustomer.getFirstName());
        loggedInCustomerResponse.setLastName(foundCustomer.getLastName());}
        else {
            throw new CustomerDoesNotExist("incorrect password");
        }
        return loggedInCustomerResponse;
    }

    @Override
    public UpdatedCustomerResponse updateCustomer(String email,UpdateRequest updateRequest) throws InvalidPhoneNumber, CustomerDoesNotExist {
        Customer foundCustomer = findCustomerByEmail(email);
        if (foundCustomer==null) throw new CustomerDoesNotExist("no such customer with this email");
        if(updateRequest.getFirstName()!=null){foundCustomer.setFirstName(updateRequest.getFirstName());}else {foundCustomer.setFirstName(foundCustomer.getFirstName());}
        if (updateRequest.getLastName()!=null){foundCustomer.setLastName(updateRequest.getLastName());}else {foundCustomer.setLastName(foundCustomer.getLastName());}
        if(updateRequest.getPassword()!=null){foundCustomer.setPassword(updateRequest.getPassword());}else {foundCustomer.setPassword(foundCustomer.getPassword());}
        if(updateRequest.getPhoneNumber()!= null){if (updateRequest.getPhoneNumber().length()!= 11) throw new InvalidPhoneNumber("Please enter a correct phone number");foundCustomer.setPhoneNumber(updateRequest.getPhoneNumber());}else {foundCustomer.setPhoneNumber(foundCustomer.getPhoneNumber());}
        if (updateRequest.getPhoneNumber()==null && updateRequest.getPassword()==null && updateRequest.getLastName()==null && updateRequest.getFirstName()==null) throw new CustomerDoesNotExist("you did not change any of your field");
        customerRepository.save(foundCustomer);
        UpdatedCustomerResponse updatedCustomerResponse = new UpdatedCustomerResponse();
        updatedCustomerResponse.setFirstName(foundCustomer.getFirstName());
        updatedCustomerResponse.setLastName(foundCustomer.getLastName());
        updatedCustomerResponse.setPhoneNumber(foundCustomer.getPhoneNumber());
        updatedCustomerResponse.setPassword(foundCustomer.getPassword());
        return updatedCustomerResponse;
    }

//    @Override
//    public TaskResponse makeTask(String email, TaskRequest taskRequest) throws CustomerDoesNotExist {
//        Customer foundCustomer = findCustomerByEmail(email);
//        if (foundCustomer==null) throw new CustomerDoesNotExist("No such customer exists");
//        Task task = new Task();
//        task.setTitle(taskRequest.getTitle());
//        task.setDescription(taskRequest.getDescription());
//        task.setStatus(taskRequest.getStatus());
//        task.setCustomer(foundCustomer);
//        foundCustomer.getTasks().add(task);
//        taskRepository.save(task);
//        TaskResponse taskResponse = new TaskResponse();
//        taskResponse.setTitle(task.getTitle());
//        taskResponse.setDescription(task.getDescription());
//        taskResponse.setStatus(task.getStatus());
//        return taskResponse;
//    }
//
    @Override
    public List<Task> fetchTask(LogInRequest logInRequest) throws CustomerDoesNotExist {
        Customer foundCustomer = customerRepository.findCustomerByEmail(logInRequest.getEmail());
        if (foundCustomer==null) throw new CustomerDoesNotExist("No such customer exist, please try passing in the correct email");
        var foundCustomerId = foundCustomer.getId();
        var allTask = taskRepository.findByCustomer_id(foundCustomerId);
        return allTask;
    }
//
//    @Override
//    public DeleteResponse deleteAllTask(LogInRequest logInRequest) throws CustomerDoesNotExist {
//        List<Task> allTask = fetchTask(logInRequest);
//        for (Task task:allTask){
//            task.setCustomer(null);
//        }
//        taskRepository.deleteAll();
//        DeleteResponse deleteResponse = new DeleteResponse();
//        deleteResponse.setMessage("delete successful");
//        return deleteResponse;
//    }
//
//    @Override
//    public DeleteResponse deleteTaskByTitle(DeleteRequest deleteRequest) throws CustomerDoesNotExist {
//        Customer foundCustomer = customerRepository.findCustomerByEmail(deleteRequest.getEmail());
//        if (foundCustomer==null) throw new CustomerDoesNotExist("No such customer exist, please try a correct email");
//        if (foundCustomer.getEmail().equals(deleteRequest.getEmail())){
//            taskRepository.delete(taskRepository.findTaskByTitle(deleteRequest.getTitle()));
//        }
//        DeleteResponse deleteResponse = new DeleteResponse();
//        deleteResponse.setMessage("delete Successful");
//        return deleteResponse;
//    }
//
//    @Override
//    public TaskResponse fetchTaskByTitle(FetchRequest fetchRequest) throws CustomerDoesNotExist {
//        TaskResponse taskResponse = new TaskResponse();
//        Customer foundCustomer =  customerRepository.findCustomerByEmail(fetchRequest.getEmail());
//        if (foundCustomer==null) {throw new CustomerDoesNotExist("No such customer exist, please try a correct email");}
//        Task foundTask = taskRepository.findTaskByTitle(fetchRequest.getTitle());
//        taskResponse.setTitle(foundTask.getTitle());
//        taskResponse.setDescription(foundTask.getDescription());
//        taskResponse.setStatus(foundTask.getStatus());
//        return taskResponse;
//    }
//
//    @Override
//    public UpdatedTaskResponse  updateTask(String title, UpdateTaskRequest updateTaskRequest) throws CustomerDoesNotExist {
//        UpdatedTaskResponse updatedTaskResponse = new UpdatedTaskResponse();
//        Customer foundCustomer = customerRepository.findCustomerByEmail(updateTaskRequest.getEmail());
//        List<Task> listOfCustomerTask = taskRepository.findByCustomer_id(foundCustomer.getId());
//        for (Task foundTask:listOfCustomerTask){
//            if (foundTask.getTitle().equals(title)){
//                if (updateTaskRequest.getTitle()!=null){foundTask.setTitle(updateTaskRequest.getTitle());}else {foundTask.setTitle(foundTask.getTitle());}
//                if (updateTaskRequest.getDescription()!=null){foundTask.setDescription(updateTaskRequest.getDescription());}else {foundTask.setDescription(foundTask.getDescription());}
//                if (updateTaskRequest.getDescription()!=null){foundTask.setStatus(updateTaskRequest.getStatus());}else {foundTask.setStatus(foundTask.getStatus());}
//                taskRepository.save(foundTask);
//                updatedTaskResponse.setMessage("you have successfully updated the task");
//            } else {
//                throw new CustomerDoesNotExist("");
//            }
//        }
//
//        return updatedTaskResponse;
//    }
}
