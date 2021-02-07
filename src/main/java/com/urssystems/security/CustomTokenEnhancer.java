package com.urssystems.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import com.urssystems.model.User;
import com.urssystems.service.UserService;

public class CustomTokenEnhancer implements TokenEnhancer {

	@Autowired
	private UserService userService;

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		User user = userService.fetchUser(authentication.getName());

		final Map<String, Object> additionalInfo = new HashMap<>();

		additionalInfo.put("id", user.getId());
		additionalInfo.put("username", user.getFirstname()+" "+user.getLastname());
		additionalInfo.put("profiles", user.getUserProfiles().get(0).getRole());

		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);

		return accessToken;
	}

}
