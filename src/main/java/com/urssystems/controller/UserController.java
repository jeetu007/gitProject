package com.urssystems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.urssystems.DTO.UserDTO;
import com.urssystems.service.UserService;

@RestController
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private SimpMessagingTemplate template;

	@GetMapping("/")
	public ResponseEntity<String> testAPI() {
		return new ResponseEntity<String>("Hello World.", HttpStatus.OK);
	}

	@GetMapping("/test/websocket")
	public ResponseEntity<String> testWebsokcet() {
		template.convertAndSend("/notify", "Websocket Info from Backend");
		return new ResponseEntity<String>("WebSocket Info", HttpStatus.OK);
	}

	@GetMapping(path = "/user/search/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> findUser(@PathVariable("userId") int userId) {
		UserDTO userDTO = userService.findUserById(userId);
		if (userDTO == null) {
			return new ResponseEntity<UserDTO>(userDTO, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
	}// findUser(-)

	@PostMapping(path = "/user/registration", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> registerUser(@RequestBody UserDTO userDTO) {
		boolean registerResult = userService.registerUser(userDTO);

		if (registerResult) {
			return new ResponseEntity<Boolean>(registerResult, HttpStatus.OK);
		}

		return new ResponseEntity<Boolean>(registerResult, HttpStatus.NOT_IMPLEMENTED);

	}// registerUser(-)

	@PostMapping(path = "/user/logout", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> userLogOut(@RequestParam("access_token") String accessToken) {
		String message = "";
		try {
			OAuth2AccessToken oauth2AccessToken = tokenStore.readAccessToken(accessToken);
			if (oauth2AccessToken == null || oauth2AccessToken.equals(null)) {
				return new ResponseEntity<String>("No such token exists !", HttpStatus.NOT_FOUND);
			}
			tokenStore.removeAccessToken(oauth2AccessToken);
			message = "You have been logged out.";
			System.out.println("You have been logged out.");
			return new ResponseEntity<String>(message, HttpStatus.OK);

		} catch (Exception e) {
			message = "Error in token store.";
			System.out.println("Error in token store.");
			return new ResponseEntity<String>(message, HttpStatus.NOT_ACCEPTABLE);
		}
	}// userLogOut(-)

}// class
