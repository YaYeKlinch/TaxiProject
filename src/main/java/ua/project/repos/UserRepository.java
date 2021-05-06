package ua.project.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.project.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
