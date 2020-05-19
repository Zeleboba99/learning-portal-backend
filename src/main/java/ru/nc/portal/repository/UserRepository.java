package ru.nc.portal.repository;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.Query;
import ru.nc.portal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
    User findUserByEmail(String email);
    User findUserById(Long id);

}
