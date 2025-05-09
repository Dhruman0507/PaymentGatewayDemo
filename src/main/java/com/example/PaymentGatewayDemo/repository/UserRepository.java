package com.example.PaymentGatewayDemo.repository;

import com.example.PaymentGatewayDemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM user WHERE email=:email", nativeQuery = true)
    Optional<User> findByEmailIgnoreCase(String email);


    Optional<User> findByEmail(String email);
}
