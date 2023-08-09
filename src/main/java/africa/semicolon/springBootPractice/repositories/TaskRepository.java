package africa.semicolon.springBootPractice.repositories;

import africa.semicolon.springBootPractice.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findByCustomer_id(Long customerId);
    Task findTaskByTitle(String title);
    void deleteByTitle(String title);
}
