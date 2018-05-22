package cn.fintecher.authorization.agent.entity;

import java.io.Serializable;

/**
 * @author hhh
 * @version 1.0.0
 * @since 1.0.0
 * @date 2016-5-17 17:36:28 
 */

public class SysUser implements Serializable {

	private static final long serialVersionUID = 8102857341612988949L;

	private Long id;
    private String username;
    private String password;
    private java.util.Date createTime;
    private Integer stauts;
    private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SysUser(){
	}

	public SysUser(
		Long id
	){
		this.id = id;
	}

    public void setId(Long value) {
        this.id = value;
    }
    
    public Long getId() {
        return this.id;
    }
    public void setUsername(String value) {
        this.username = value;
    }
    
    public String getUsername() {
        return this.username;
    }
    public void setPassword(String value) {
        this.password = value;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public void setCreateTime(java.util.Date value) {
        this.createTime = value;
    }
    
    public java.util.Date getCreateTime() {
        return this.createTime;
    }
    public void setStauts(Integer value) {
        this.stauts = value;
    }
    
    public Integer getStauts() {
        return this.stauts;
    }  
}

