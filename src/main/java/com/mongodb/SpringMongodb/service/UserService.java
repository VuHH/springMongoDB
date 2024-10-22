package com.mongodb.SpringMongodb.service;

import com.mongodb.SpringMongodb.dto.AgeGroupCount;
import com.mongodb.SpringMongodb.entity.User;
import com.mongodb.SpringMongodb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final MongoTemplate mongoTemplate;

    @Autowired
    public UserService(UserRepository userRepository, MongoTemplate mongoTemplate) {
        this.userRepository = userRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserByName(String name) {
        return userRepository.findByName(name);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public List<User> getListUserByAgeGreater(int age) {
        return userRepository.findByAgeGreaterThan(age);
    }

    public List<User> getUsersByAgeAndEmail(int minAge, String emailRegex) {

        Criteria criteria = new Criteria();
        criteria.and("age").gt(minAge).and("email").regex(emailRegex, "i");

        Query query = new Query(criteria);

        return mongoTemplate.find(query, User.class);
    }

    public Page<User> getUsersWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable);
    }

    public List<User> findUsersByDynamicCriteria(String name, Integer minAge, Integer maxAge) {
        Query query = new Query();

        if (name != null && !name.isEmpty()) {
            query.addCriteria(Criteria.where("name").regex(name, "i"));
        }

        if (minAge != null && maxAge != null) {
            query.addCriteria(Criteria.where("age").gte(minAge).lte(maxAge));
        }

        query.fields().include("name").include("age");

        return mongoTemplate.find(query, User.class);
    }


    public List<AgeGroupCount> countUsersByAgeGroup() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.group("age").count().as("count"),
                Aggregation.project("count").and("age").previousOperation(),
                Aggregation.sort(Sort.Direction.ASC, "age")

        );

        AggregationResults<AgeGroupCount> results = mongoTemplate.aggregate(aggregation, "users", AgeGroupCount.class);
        return results.getMappedResults();
    }
}
