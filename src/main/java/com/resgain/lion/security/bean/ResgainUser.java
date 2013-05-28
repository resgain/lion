package com.resgain.lion.security.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Spring Security的UserDetails的一个实现
 * @author memphis.guo
 */
public class ResgainUser implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private String id, nickname, name, pass;
	private boolean enabled;
	private Collection<GrantedAuthority> authority;
	private List<String> authList = new ArrayList<String>();
	
	public ResgainUser(String id, String code, String nickname, String pass, boolean enabled, Collection<GrantedAuthority> authority)
	{
		this.id = id;
		this.name = code;
		this.nickname = name;		
		this.pass = pass;
		this.enabled = enabled;
		this.authority = authority;
		setAuthList(authority);
	}	
	
	/**
	 * 是否拥有指定的权限，可以是逗号分隔的权限名称，如果拥有其中一个则返回true
	 * @param auth
	 * @return
	 */
	public boolean hasAuth(String auth)
	{
		if(auth!=null)
		{
			String a[] = auth.split(",");
			for (String s : a) {
				if(authList.contains(s))
					return true;
			}
		}
		return false;
	}
	
	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return authority;
	}

	@Override
	public String getPassword() {
		return pass;
	}

	@Override
	public String getUsername() {
		return name;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}


	public Collection<GrantedAuthority> getAuthority() {
		return authority;
	}
	public void setAuthority(Collection<GrantedAuthority> authority) {
		this.authority = authority;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	private void setAuthList(Collection<GrantedAuthority> auth)
	{
		if(auth!=null)
			for (GrantedAuthority a : auth) {
				authList.add(a.getAuthority());
			}
	}

	public String getNickname()
	{
		return nickname;
	}
}
