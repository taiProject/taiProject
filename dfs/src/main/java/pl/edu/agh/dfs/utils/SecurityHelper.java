package pl.edu.agh.dfs.utils;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityHelper {

	/**
	 * Checks if current user has role
	 * 
	 * @param role
	 *            role to be checked
	 * @return
	 */
	public static boolean hasUserRole(String role) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

		for (GrantedAuthority authority : authorities) {
			if (role.equals(authority.getAuthority())) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 
	 * @return current user's name
	 */
	public static String getUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}

	/**
	 * 
	 * @return current user's authorities
	 */
	public static Collection<? extends GrantedAuthority> getAuthorities() {
		return SecurityContextHolder.getContext().getAuthentication().getAuthorities();
	}
}
