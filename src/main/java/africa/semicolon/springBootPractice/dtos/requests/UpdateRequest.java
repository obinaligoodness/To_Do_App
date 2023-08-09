package africa.semicolon.springBootPractice.dtos.requests;

import lombok.Data;

@Data
public class UpdateRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;

}
