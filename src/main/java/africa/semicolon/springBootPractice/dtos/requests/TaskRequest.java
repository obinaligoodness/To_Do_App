package africa.semicolon.springBootPractice.dtos.requests;

import africa.semicolon.springBootPractice.models.Status;
import lombok.Data;

@Data
public class TaskRequest {
    private Long id;
    private String title;
    private String description;
    private Status status;
}
