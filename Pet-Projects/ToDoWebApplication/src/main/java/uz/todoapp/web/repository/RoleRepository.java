package uz.todoapp.web.repository;

import uz.todoapp.web.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
    // Other methods specific to Role can be added here
}

