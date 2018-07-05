package cn.fintecher.sms.dao;

import java.util.List;

import cn.fintecher.sms.entity.SysRoleEntity;

/**
 * 角色管理
 * 
 */
public interface SysRoleDao extends BaseDao<SysRoleEntity> {
	
	/**
	 * 查询用户创建的角色ID列表
	 */
	List<Long> queryRoleIdList(Long createUserId);
}
