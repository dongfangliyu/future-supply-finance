package cn.fintecher.authorization.manager.userdetails.entity;

import java.io.Serializable;

/**
 * @author hhh
 * @version 1.0.0
 * @since 1.0.0
 * @date 2016-5-17 17:36:28
 */

public class SysUserRole implements Serializable {
	
	private static final long serialVersionUID = -3476789634552285294L;
	
	private Long id;
	private Long userId;
	private String roleCode;

	public SysUserRole() {
	}

	public SysUserRole(Long id) {
		this.id = id;
	}

	public void setId(Long value) {
		this.id = value;
	}

	public Long getId() {
		return this.id;
	}

	public void setUserId(Long value) {
		this.userId = value;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setRoleCode(String value) {
		this.roleCode = value;
	}

	public String getRoleCode() {
		return this.roleCode;
	}

}
