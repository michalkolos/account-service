package com.michalkolos.account.api.auth;

import com.michalkolos.account.api.auth.dto.DtoSignupUser;
import com.michalkolos.account.api.auth.dto.DtoSignupUserResponse;
import com.michalkolos.account.api.exceptions.IllegalRequestBodyException;
import com.michalkolos.account.api.exceptions.UnspecifiedServerException;
import com.michalkolos.account.api.exceptions.UserExistException;
import com.michalkolos.account.api.security.Utilities;
import com.michalkolos.account.api.security.userStore.Role;
import com.michalkolos.account.api.security.userStore.User;
import com.michalkolos.account.api.security.userStore.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final UserRepository userRepository;
	private final PasswordEncoder encoder;

	@Autowired
	public AuthController(UserRepository userRepository, PasswordEncoder encoder) {
		this.userRepository = userRepository;
		this.encoder = encoder;
	}



	@PostMapping("/signup")
	public ResponseEntity<DtoSignupUserResponse> acceptSignupRequest (@RequestBody DtoSignupUser signupUser) {

		return 	Optional.ofNullable(signupUser)
				.filter(this::validateSignupUser).or(() -> { throw new IllegalRequestBodyException(); })
				.filter(su -> isEmailFree(su.getEmail())).or(() -> { throw new UserExistException(); })
				.map(this::convertSignupUserToUser)
				.map(userRepository::save)
				.map(this::convertUserToSignupUserResponse)
				.map(usrRes -> new ResponseEntity<>(usrRes, HttpStatus.OK))
				.orElseThrow(UnspecifiedServerException::new);
	}

	private boolean verifyEmail(String email) {
		String[] parts = email.split("@");

		return parts.length == 2 && parts[1].equals("acme.com");
	}



	private boolean validateSignupUser(DtoSignupUser user) {
		return  Optional.ofNullable(user)
				.filter(u -> u.getName() != null && !u.getName().isBlank())
				.filter(u -> u.getLastname() != null && !u.getLastname().isBlank())
				.filter(u -> u.getEmail() != null && Utilities.validateEmailAddress(u.getEmail()))
				.filter(u -> u.getPassword() != null && !u.getPassword().isBlank())
				.isPresent();
	}

	private boolean isEmailFree(String email) {
		return userRepository.findByUsername(email
				.toLowerCase(Locale.ROOT))
				.isEmpty();
	}

	private User convertSignupUserToUser(DtoSignupUser su) {
		return new User(
				su.getEmail().toLowerCase(Locale.ROOT),
				encoder.encode(su.getPassword()),
				su.getName(),
				su.getLastname(),
				new Role[]{Role.ROLE_USER});
	}

	private DtoSignupUserResponse convertUserToSignupUserResponse(User user) {
		return new DtoSignupUserResponse(
				user.getId(),
				user.getName(),
				user.getLastName(),
				user.getUsername());
	}
}
