package africa.semicolon.springBootPractice.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FetchRequest {
    private String email;
    private String title;
}
