package pl.edu.agh.dfs.authentication;

import java.util.LinkedList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();

		SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
		List<GrantedAuthority> authorities = new LinkedList<GrantedAuthority>();
		authorities.add(authority);

		if (username.equals("admin") && password.equals("123")) {
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

			return new UsernamePasswordAuthenticationToken(username, password, authorities);
		} else if (username.equals("user") && password.equals("123")) {
			return new UsernamePasswordAuthenticationToken(username, password, authorities);
		}
		throw new BadCredentialsException("Bad credentials");
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
