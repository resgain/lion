package com.resgain.lion.security.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.resgain.lion.entity.base.Employee;
import com.resgain.lion.security.bean.ResgainUser;
import com.resgain.lion.service.base.EmployeeService;

/**
 * Spring Security的UserDetailsService接口实现类，实现用户认证的入口
 * @author memphis.guo
 */
public class SecurityUserService implements UserDetailsService 
{
	@Autowired private EmployeeService employeeService;
	
	@Override
	public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException, DataAccessException {
		Employee emp = employeeService.getEmpByCode(loginId);
		if(emp==null)
			throw new UsernameNotFoundException("用户[" + loginId + " ]不存在");
		ResgainUser ret = new ResgainUser(emp.getId(), emp.getCode(), emp.getName(), emp.getPass(), emp.getStatus()<3, null);
		List<GrantedAuthority> t = new ArrayList<GrantedAuthority>();
		t.add(new GrantedAuthority(){
			private static final long serialVersionUID = 1L;
			@Override
			public String getAuthority() {
				return "ROLE_USER";
			}
		});
		
		if(!emp.isDelFlag()){
			
		}
		ret.setAuthority(t);
		return ret;
	}
}