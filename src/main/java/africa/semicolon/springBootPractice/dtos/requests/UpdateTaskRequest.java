package africa.semicolon.springBootPractice.dtos.requests;

import africa.semicolon.springBootPractice.models.Status;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateTaskRequest {
    private String email;
    private String title;
    private String description;
    private Status status;
}
