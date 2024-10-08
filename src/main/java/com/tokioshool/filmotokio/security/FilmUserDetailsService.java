package com.tokioshool.filmotokio.security;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tokioshool.filmotokio.domain.Role;
import com.tokioshool.filmotokio.domain.User;
import com.tokioshool.filmotokio.service.UserService;

@Service
public class FilmUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserService userService;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User oldUser = userService.findByActive(true);
		if(oldUser != null) {
			oldUser.setActive(false);
		}
		
		User user = userService.findByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException("Início de sessão ou palavra-passe incorrectos");
		}
		user.setActive(true);
		userService.modifyLastLogin(user.getId());
		List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
		return buildUserForAuthentication(user, authorities);
	}
	
	private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles){
		Set<GrantedAuthority> roles = new HashSet<>();
		userRoles.forEach(role->roles.add(new SimpleGrantedAuthority(role.getName())));
		return new ArrayList<>(roles);
	}
	
	private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.isActive(), true, true, true, authorities);
	}
}
