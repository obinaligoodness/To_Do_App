package africa.semicolon.springBootPractice.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CombinedRequest {
    private LogInRequest logInRequest;
    private TaskRequest taskRequest;
}
