package africa.semicolon.springBootPractice.repositories;

import africa.semicolon.springBootPractice.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
 Customer findCustomerByEmail(String email);
}
