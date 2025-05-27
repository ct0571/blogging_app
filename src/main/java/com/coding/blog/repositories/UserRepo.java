package com.coding.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coding.blog.entities.User;

public interface UserRepo extends JpaRepository<User, Integer> {

}
