package cn.fintecher.authorization.manager.userdetails.dao;


import cn.fintecher.authorization.manager.userdetails.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author hhh
 * @version 1.0.0
 * @since 1.0.0
 * @date 2016-5-17 17:36:28 
 */
@Mapper
public interface SysUserDao {
     SysUser getByUserName(String username);
     void createUser(SysUser sysUser);
     void updateUser(SysUser sysUser);
}
