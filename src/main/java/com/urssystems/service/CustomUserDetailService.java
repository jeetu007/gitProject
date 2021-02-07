package com.urssystems.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.urssystems.model.User;
import com.urssystems.model.UserProfile;

@Component
@Qualifier("customUserDetailService")
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userService.fetchUser(username);

		if (user == null) {
			throw new UsernameNotFoundException("User not found.");
		}

		return new org.springframework.security.core.userdetails.User(username, user.getPassword(), user.getStatus().equalsIgnoreCase("ACTIVE"), true, true,
				true, getUserAuthorities(user));

	}

	private Collection<? extends GrantedAuthority> getUserAuthorities(User user) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (UserProfile userProfile : user.getUserProfiles()) {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + userProfile.getRole()));
		}
		System.out.println(authorities);
		return authorities;
	}

}
