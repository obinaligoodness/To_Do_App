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
import com.auth0.jwt.algorithms.Algorithm;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;

import java.util.Date;
import java.util.List;


@Service
@AllArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerServiceInterface{
    private final CustomerRepository customerRepository;
    private final TaskRepository taskRepository;
    private final EmailServiceInterface emailService;
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
        else{
            String generatedToken = generateToken(registrationRequest.getEmail());
            sendVerificationEmail(registrationRequest.getEmail(),generatedToken);
            customerRepository.save(customer);
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

    @Override
    public List<Task> fetchTask(LogInRequest logInRequest) throws CustomerDoesNotExist {
        Customer foundCustomer = customerRepository.findCustomerByEmail(logInRequest.getEmail());
        if (foundCustomer==null) throw new CustomerDoesNotExist("No such customer exist, please try passing in the correct email");
        var foundCustomerId = foundCustomer.getId();
        var allTask = taskRepository.findByCustomer_id(foundCustomerId);
        return allTask;
    }

    @Override
    public void sendVerificationEmail(String toEmail, String verificationToken) {
            String subject = "Email Verification";
            String content = "Click the following link to verify your email: " +
                    "https://localhost:8080/verify-email?token=" + verificationToken;
            emailService.sendEmail(toEmail, subject, content);
        }

    @Override
    public String generateToken(String email) {
        return JWT.create()
                .withSubject(email)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600))
                .sign(Algorithm.HMAC512("secret"));
    }

}

