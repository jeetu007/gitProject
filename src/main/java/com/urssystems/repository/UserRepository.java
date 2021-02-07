package com.urssystems.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.urssystems.model.User;

@Repository("userRepository")
@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {

	public User findByUsername(String username);
	
	public User findById(int userId);
}
