package com.urssystems.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.urssystems.model.UserProfile;

@Repository("userProfileRepository")
@Transactional
public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {

	public UserProfile findByRole(String role);
	
}
