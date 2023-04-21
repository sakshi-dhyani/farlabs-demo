package com.farelabs.Authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.farelabs.entity.Admin;
import com.farelabs.entity.User;
import com.farelabs.exception.ResourceNotFoundException;
import com.farelabs.repository.AdminRepository;
import com.farelabs.repository.UserRepository;


@Service
public class CustomUserDetailService implements UserDetailsService{
	

	@Autowired 
	private UserRepository userRepository;
	
	@Autowired
	private AdminRepository adminRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//find user
		User findUser = userRepository.findByEmail(username);
		Admin findAdmin = adminRepository.findByEmail(username);
		
		User user = null;
		Admin admin = null;
		
		if (findUser != null) {
			user = this.userRepository.findByEmailAndActive(username, (byte) 1)
					.orElseThrow(() -> new ResourceNotFoundException("user", "email", "username"));

			return user;
		}
		
		 else if (findAdmin != null)
				admin = this.adminRepository.findByEmailAndActive(username, (byte) 1)
						.orElseThrow(() -> new ResourceNotFoundException("user", "email", "username"));
			return admin;
}


}
