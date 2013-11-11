package pl.edu.agh.dfs.authentication;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import pl.edu.agh.dfs.daos.UserDao;
import pl.edu.agh.dfs.models.User;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	@Autowired
	private UserDao userDao;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();

		List<GrantedAuthority> authorities = new LinkedList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

		User user = userDao.select(username);
		if (user != null) {
			if (username.equals(user.getLogin()) && password.equals(user.getPassword())) {
				if (user.getRoleName().equals("ROLE_ADMIN")) {
					authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
				}
				return new UsernamePasswordAuthenticationToken(username, password, authorities);
			}
		}
		throw new BadCredentialsException("Bad credentials");
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
