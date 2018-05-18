package cn.fintecher.common.utils;

import java.io.Serializable;
import java.util.Set;

@SuppressWarnings("serial")
public class RoleSet implements Serializable {
	private Set<String> roles;

	public RoleSet(){
		
	}
	
	public RoleSet(Set<String> roles){
		this.roles = roles;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
	


}
