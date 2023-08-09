package africa.semicolon.springBootPractice.dtos.requests;

import lombok.Data;

@Data
public class RegistrationRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
}
