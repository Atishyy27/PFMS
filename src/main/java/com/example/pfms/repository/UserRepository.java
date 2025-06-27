package com.example.pfms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.pfms.model.User;

import java.util.Optional; // Import Optional for findByUsername

/**
 * Repository interface for User entities.
 * Extends JpaRepository to provide standard CRUD operations
 * and Spring Data JPA magic for custom queries.
 */
@Repository // Marks this interface as a Spring Data JPA repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a User entity by its username.
     * Spring Data JPA automatically generates the query based on the method name.
     *
     * @param username The username to search for.
     * @return An Optional containing the User if found, or an empty Optional if not.
     */
    Optional<User> findByUsername(String username);

    /**
     * Checks if a User with the given username exists.
     *
     * @param username The username to check.
     * @return True if a user with the username exists, false otherwise.
     */
    Boolean existsByUsername(String username);
}
// package com.example.pfms.repository;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;
// import com.example.pfms.model.User;
// import java.util.Optional;

// @Repository
// public interface UserRepository extends JpaRepository<User, Long> {
//     // Optionally define custom query methods like:
//     // User findByUsername(String username);
//     Optional<User> findByUsername(String username);
// }
