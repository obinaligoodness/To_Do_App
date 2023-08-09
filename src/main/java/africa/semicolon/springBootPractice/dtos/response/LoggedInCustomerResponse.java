package africa.semicolon.springBootPractice.dtos.response;

import lombok.Data;

@Data
public class LoggedInCustomerResponse {
    private String firstName;
    private String lastName;
}
