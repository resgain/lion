package com.resgain.lion.security.filter;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * 访问决策决定某个用户具有的角色是否有足够的权限去访问某个资源
 * @author memphis.guo
 */
public class ResgainAccessDecisionManager implements AccessDecisionManager {

	@Override
	public void decide(Authentication auth, Object obj, Collection<ConfigAttribute> confa) throws AccessDeniedException, InsufficientAuthenticationException {
		if (confa == null)
			return;
		Iterator<ConfigAttribute> ite = confa.iterator();
		while (ite.hasNext()) {
			ConfigAttribute ca = ite.next();
			String needAuth = ((SecurityConfig) ca).getAttribute();
			for (GrantedAuthority ga : auth.getAuthorities()) {
				if (needAuth.equals(ga.getAuthority())) {
					return;
				}
			}
		}
		throw new AccessDeniedException("没有权限，拒绝存取!");
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}
}
