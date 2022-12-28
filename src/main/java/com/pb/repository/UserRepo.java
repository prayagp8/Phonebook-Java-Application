package com.pb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pb.model.User;

public interface UserRepo extends JpaRepository<User, Integer>{

	public User findByEmail(String email);
}
