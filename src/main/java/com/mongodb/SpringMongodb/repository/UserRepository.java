package com.mongodb.SpringMongodb.repository;

import com.mongodb.SpringMongodb.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByName(String name);

    @Query("{'age' : { $gt: ?0}}")
    List<User> findByAgeGreaterThan(int age);

    Page<User> findAll(Pageable pageable);
}
