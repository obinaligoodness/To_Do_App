package africa.semicolon.springBootPractice.dtos.response;

import lombok.Data;
import lombok.Setter;

@Data
public class FoundCustomerResponse {
    private String email;
    private String phoneNumber;
}
