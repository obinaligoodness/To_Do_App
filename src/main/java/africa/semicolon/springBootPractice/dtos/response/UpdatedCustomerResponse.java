package africa.semicolon.springBootPractice.dtos.response;

import lombok.Data;

@Data
public class UpdatedCustomerResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;

}
