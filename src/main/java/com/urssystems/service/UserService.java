package com.urssystems.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urssystems.DTO.UserDTO;
import com.urssystems.model.User;
import com.urssystems.model.UserProfile;
import com.urssystems.repository.UserProfileRepository;
import com.urssystems.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	UserProfileRepository userProfileRepository;
	
	@Autowired
	ModelMapper modelMapper;

	public User fetchUser(String username) {
		return userRepository.findByUsername(username);
	}// fetchUser(-)

	public UserDTO findUserById(int userId) {
		try {
			return modelMapper.map(userRepository.findById(userId), UserDTO.class);
		} catch (Exception e) {
			return null;
		}
	}// findUserById(-)

	public boolean registerUser(UserDTO userDTO){		
		try {
			List<UserProfile> userProfileList = new ArrayList<UserProfile>();
			
			userProfileList.add(userProfileRepository.findByRole(userDTO.getUserProfiles().get(0).getRole()));
			userDTO.setUserProfiles(userProfileList);
			
			User user = modelMapper.map(userDTO, User.class);
			user.setCreatedOn(new Date(System.currentTimeMillis()));
			user.setModifiedOn(new Date(System.currentTimeMillis()));
			user.setStatus("ACTIVE");
			User registerUser = userRepository.save(user);
			
			if(registerUser == null) {
				return false;
			} else {
				return true;
			}
		}catch(Exception e){
			return false;
		}
	}//registerUser(-)
	
}//class