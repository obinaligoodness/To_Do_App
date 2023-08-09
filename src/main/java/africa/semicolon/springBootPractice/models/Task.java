package africa.semicolon.springBootPractice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerId") // Name should match the column in the Task table
    private Customer customer;


    @Enumerated(value = EnumType.STRING)
    private Status status;
}
