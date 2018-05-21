package cn.fintecher.authorization.common.dto.userdetails;

import cn.fintecher.authorization.common.dto.functionDetails.FunctionDetailInfo;
import cn.fintecher.authorization.common.dto.roledetails.RoleDetailInfo;

import java.util.List;

public class UserDetailInfo {

    private String username;//登录账号

    private String password;//密码

    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;

    private List<RoleDetailInfo> roles;
    
    private List<FunctionDetailInfo> functions;
    
   

    public List<FunctionDetailInfo> getFunctions() {
		return functions;
	}

	public void setFunctions(List<FunctionDetailInfo> functions) {
		this.functions = functions;
	}

	public List<RoleDetailInfo> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDetailInfo> roles) {
        this.roles = roles;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
