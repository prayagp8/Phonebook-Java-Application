package com.pb.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pb.model.User;
import com.pb.repository.UserRepo;


@Service	
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		logic 
		
		 User u = userRepo.findByEmail(username);
		 
		 if(u!=null) {
			 return new SecurityUser(u);     //userdetails object
		 }else {
			 throw new UsernameNotFoundException("user not found!!");
		 }
		
		
	}

}
