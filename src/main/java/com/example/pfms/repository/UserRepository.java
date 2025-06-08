package com.example.pfms.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.pfms.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Optionally define custom query methods like:
    User findByUsername(String username);
}
