package uz.todoapp.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.todoapp.web.model.User;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    // Additional query methods related to User can be added here
}

