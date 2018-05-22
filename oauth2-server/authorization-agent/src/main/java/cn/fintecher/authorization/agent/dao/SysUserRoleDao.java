package cn.fintecher.authorization.agent.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * @author hhh
 * @version 1.0.0
 * @since 1.0.0
 * @date 2016-5-17 17:36:28 
 */
@Mapper
public interface SysUserRoleDao {
	List<String> findRolesByUsername(String username);
	List<String> findFunctionByUsername(String username);
}
