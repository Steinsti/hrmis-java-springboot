package io.github.steinsti.hrims.repository;

import io.github.steinsti.hrims.model.User;
import java.util.UUID;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, UUID>{
    Optional <User> findByUsername(String username);
    
}
