package africa.semicolon.springBootPractice.dtos.response;

import africa.semicolon.springBootPractice.models.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private Status status;
}
