package com.michalkolos.account.api.security.userStore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	@Autowired
	public UserDetailsServiceImpl(UserRepository userRepo) {
		this.userRepository = userRepo;
	}

	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		return  userRepository.findByUsername(username.toLowerCase(Locale.ROOT))
				.orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));
	}
}