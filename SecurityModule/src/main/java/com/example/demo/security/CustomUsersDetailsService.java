package com.example.demo.security;


import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;



@Service
public class CustomUsersDetailsService implements UserDetailsService{

	@Autowired
	UserRepository usersRepository;
	
	@Override	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User users = usersRepository.findByEmail(username);
		if (users == null)
			throw new UsernameNotFoundException("User Not Found");
		Set<GrantedAuthority> authorities = new HashSet<>();
//		authorities.add(new SimpleGrantedAuthority();
		return new org.springframework.security.core.userdetails.User(users.getEmail(), users.getPassword(), authorities);
	}

}
