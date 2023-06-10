package com.example.FCMTest.user.repository;

import com.example.FCMTest.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByFirebaseToken(String firebaseToken);
    Optional<User> findById(Long id);
}
