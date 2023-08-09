package africa.semicolon.springBootPractice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,mappedBy = "customer")
    @JsonIgnore
    private List<Task> tasks = new ArrayList<>();
}
