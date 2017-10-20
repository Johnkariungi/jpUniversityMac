package com.jpUniversity.repository;

import com.jpUniversity.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username); /*define method for interface to return username using Crud Spring Boot*/
}
