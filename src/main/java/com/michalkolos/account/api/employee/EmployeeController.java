package com.michalkolos.account.api.employee;

import com.michalkolos.account.api.auth.dto.DtoSignupUserResponse;
import com.michalkolos.account.api.exceptions.UnauthorizedUserException;
import com.michalkolos.account.api.security.userStore.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/api/empl")
public class EmployeeController {
	@GetMapping("/payment")
	public ResponseEntity<DtoSignupUserResponse> getAuthUserData (Authentication auth) {

		return Optional.ofNullable(auth)
				.map(a -> {
					User user;
					try {
						user = (User) a.getPrincipal();
					} catch (UsernameNotFoundException e) {
						throw new UnauthorizedUserException();
					}
					return user;
				})
				.map(u -> new ResponseEntity<>(new DtoSignupUserResponse(u), HttpStatus.OK))
				.orElseThrow(UnauthorizedUserException::new);
	}
}
