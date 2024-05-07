package com.springcompletablefuture.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springcompletablefuture.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer>{

}
