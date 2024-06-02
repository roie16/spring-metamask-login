package com.example.metamasklogin.repo;

import com.example.metamasklogin.model.MyAwesomeUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<MyAwesomeUser, String> {
        Optional<MyAwesomeUser> findByAddress(String address);

        Optional<MyAwesomeUser> findByUsername(String address);
}
