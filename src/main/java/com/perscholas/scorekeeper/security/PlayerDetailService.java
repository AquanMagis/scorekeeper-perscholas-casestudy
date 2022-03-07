package com.perscholas.scorekeeper.security;

import com.perscholas.scorekeeper.dao.PlayerDAO;
import com.perscholas.scorekeeper.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class PlayerDetailService implements UserDetailsService {
	@Autowired
	private PlayerDAO playerDAO;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Player player = playerDAO.findByUsername(username);
		if(player == null){
			throw new UsernameNotFoundException("Username " + username + " not found.");
		}

		boolean accountIsEnabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		Collection<? extends GrantedAuthority> roles = new ArrayList<>();

		return new User(username, player.getPassword(), accountIsEnabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, roles);
	}
}
