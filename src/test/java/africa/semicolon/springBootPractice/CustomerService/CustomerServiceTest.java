package africa.semicolon.springBootPractice.CustomerService;

import africa.semicolon.springBootPractice.dtos.requests.LogInRequest;
import africa.semicolon.springBootPractice.dtos.requests.RegistrationRequest;
import africa.semicolon.springBootPractice.dtos.requests.UpdateRequest;
import africa.semicolon.springBootPractice.dtos.response.LoggedInCustomerResponse;
import africa.semicolon.springBootPractice.dtos.response.RegistrationResponse;
import africa.semicolon.springBootPractice.dtos.response.UpdatedCustomerResponse;
import africa.semicolon.springBootPractice.exceptions.CustomerDoesNotExist;
import africa.semicolon.springBootPractice.exceptions.DuplicateCustomerException;
import africa.semicolon.springBootPractice.exceptions.InvalidEmailException;
import africa.semicolon.springBootPractice.exceptions.InvalidPhoneNumber;
import africa.semicolon.springBootPractice.models.Task;
import africa.semicolon.springBootPractice.services.CustomerServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Slf4j
public class CustomerServiceTest {
    @Autowired
    private CustomerServiceInterface customerServiceInterface;



    @Test
    public void testThatCustomerCanRegister() throws DuplicateCustomerException, InvalidPhoneNumber, InvalidEmailException {
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setFirstName("Goodness");
        registrationRequest.setLastName("Obinali");
        registrationRequest.setEmail("obinaligoodness@gmail.com");
        registrationRequest.setPassword("goodobin");
        registrationRequest.setPhoneNumber("08031286941");
       RegistrationResponse registeredCustomer =  customerServiceInterface.registerCustomer(registrationRequest);
       assertNotNull(registeredCustomer);
    }
    @Test
    public void testThatCustomerCanLogin() throws CustomerDoesNotExist {
        LogInRequest logInRequest = new LogInRequest();
        logInRequest.setEmail("obinaligoodness@gmail.com");
        logInRequest.setPassword("goodobin");
        LoggedInCustomerResponse loggedInCustomer = customerServiceInterface.logIn(logInRequest);
        assertNotNull(loggedInCustomer);
    }
    @Test
    public void testThatCustomerCanUpdateField() throws InvalidPhoneNumber, CustomerDoesNotExist {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.setFirstName("Mmesomachi");
        updateRequest.setLastName("Micheal");
        UpdatedCustomerResponse updatedCustomer = customerServiceInterface.updateCustomer("obinaligoodness@gmail.com",updateRequest);
        assertEquals("08031286941",updatedCustomer.getPhoneNumber());
    }
    @Test
    public void testThatCustomerCanCheckTask() throws CustomerDoesNotExist {
        LogInRequest logInRequest = new LogInRequest();
        logInRequest.setPassword("victor123");
        logInRequest.setEmail("obinaliVictor@gmail.com");
        var tasks = customerServiceInterface.fetchTask(logInRequest);
        assertNotNull(tasks);
    }

    @Test
    public void testThatCustomerCanDeleteTask(){
    }
    }

