package africa.semicolon.springBootPractice.dtos.requests;

import lombok.Data;

@Data
public class LogInRequest {
    private String email;
    private String password;
}
