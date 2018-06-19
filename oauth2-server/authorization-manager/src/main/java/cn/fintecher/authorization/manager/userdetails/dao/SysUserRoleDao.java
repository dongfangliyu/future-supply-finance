package cn.fintecher.authorization.manager.userdetails.dao;

import cn.fintecher.authorization.manager.userdetails.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author hhh
 * @version 1.0.0
 * @since 1.0.0
 * @date 2016-5-17 17:36:28 
 */
@Mapper
public interface SysUserRoleDao {
	List<String> findRolesByUsername(String username);

	void insert (SysUserRole sysUserRole);
}
