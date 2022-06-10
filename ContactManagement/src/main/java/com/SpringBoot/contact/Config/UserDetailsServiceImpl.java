package com.SpringBoot.contact.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.SpringBoot.contact.Dao.UserRepo;
import com.SpringBoot.contact.Model.User;

public class UserDetailsServiceImpl  implements UserDetailsService{

	@Autowired
	private UserRepo userrepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userrepo.getUserName(username);
			
			if(user==null) {
				
				throw new UsernameNotFoundException("User not Found...!!");	
				
			}
			CustomeUserDetails customedetails=new CustomeUserDetails(user);
 
		
		return customedetails;
	}

}
