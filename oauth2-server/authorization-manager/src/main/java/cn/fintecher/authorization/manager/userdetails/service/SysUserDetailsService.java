package cn.fintecher.authorization.manager.userdetails.service;


import cn.fintecher.authorization.common.dto.roledetails.RoleDetailInfo;
import cn.fintecher.authorization.common.dto.userdetails.UserDetailInfo;
import cn.fintecher.authorization.manager.userdetails.dao.SysUserDao;
import cn.fintecher.authorization.manager.userdetails.dao.SysUserRoleDao;
import cn.fintecher.authorization.manager.userdetails.entity.SysUser;
import cn.fintecher.authorization.manager.userdetails.entity.SysUserRole;
import cn.fintecher.common.utils.basecommon.message.Message;
import cn.fintecher.common.utils.basecommon.message.MessageType;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SysUserDetailsService {

	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	
	@Autowired
	private SysUserDao sysUserDao;

	public  Message getByUserName(String userName){
        UserDetailInfo userDetailInfo = new UserDetailInfo();
        SysUser sysUser = sysUserDao.getByUserName(userName);
        if(sysUser!=null){
            userDetailInfo.setUsername(sysUser.getUsername());
            userDetailInfo.setPassword(sysUser.getPassword());
        }else{
            return new Message(MessageType.MSG_ERROR,"oauth2", null);
        }
        return new Message(MessageType.MSG_SUCCESS,"oauth2", userDetailInfo);
    }

	
    public ResponseEntity<Message> SearchUserDetail(String username) {

        System.out.println("== SearchUserDetail ==");

        UserDetailInfo userDetailInfo = new UserDetailInfo();

        SysUser sysUser = sysUserDao.getByUserName(username);

        if(sysUser!=null){
        	userDetailInfo.setUsername(sysUser.getUsername());
        	userDetailInfo.setPassword(sysUser.getPassword());
        }
        
        List<RoleDetailInfo> roles = new ArrayList<RoleDetailInfo>();
        List<String> roleCodes = sysUserRoleDao.findRolesByUsername(username);
        if(roleCodes!=null && roleCodes.size()>0){
        	for (String roleCode : roleCodes) {
        		RoleDetailInfo roleInfo = new RoleDetailInfo();
                roleInfo.setRole(roleCode);
                roles.add(roleInfo);
			}
        }
        userDetailInfo.setRoles(roles);
        return new ResponseEntity<Message>(new Message(MessageType.MSG_SUCCESS,"oauth2", userDetailInfo), HttpStatus.OK);

    }

    public Message createUser(JSONObject map){
        try {
            if(map.containsKey("username")&& map.get("username")!=null && !"".equals(map.get("username"))){
                if(map.containsKey("password")&& map.get("password")!=null && !"".equals(map.get("password"))){
                    SysUser sysUser = sysUserDao.getByUserName(map.get("username").toString());
                    if(sysUser!=null && sysUser.getId()>0){
                        return new Message(MessageType.MSG_ERROR, "oauth2","账号已存在");
                    }else{
                        sysUser = new SysUser();
                        sysUser.setCreateTime(new Date());
                        if(map.containsKey("name"))sysUser.setName(map.get("name").toString());
                        sysUser.setStauts(1);
                        sysUser.setUsername(map.get("username").toString());
                        sysUser.setPassword(map.get("password").toString());
                        sysUserDao.createUser(sysUser);
                        if(sysUser.getId() != null && sysUser.getId() > 0){
                            SysUserRole sysUserRole = new SysUserRole();
                            sysUserRole.setRoleCode("user");
                            sysUserRole.setUserId(sysUser.getId());
                            sysUserRoleDao.insert(sysUserRole);
                        }
                        return  new Message(MessageType.MSG_SUCCESS, "oauth2",sysUser.getId());
                    }
                }else{
                    return new Message(MessageType.MSG_ERROR, "oauth2","密码为空");
                }
            }else{
                return new Message(MessageType.MSG_ERROR, "oauth2","用户名或手机号码为空");
            }
        }catch (Exception ex){
            return new Message(MessageType.MSG_ERROR, "oauth2",ex.getMessage());
        }

    }

    public Message updateUser(JSONObject map){
        try {
            if(map.containsKey("username")&& map.get("username")!=null && !"".equals(map.get("username"))){
                SysUser sysUser = sysUserDao.getByUserName(map.get("username").toString());
                if(sysUser!=null && sysUser.getId()>0){
                    if(map.containsKey("name"))sysUser.setName(map.get("name").toString());
                    sysUser.setStauts(1);
                    sysUser.setUsername(map.get("username").toString());
                    if(map.containsKey("password"))sysUser.setPassword(map.get("password").toString());
                    sysUserDao.updateUser(sysUser);
                    return  new Message(MessageType.MSG_SUCCESS, "oauth2","修改用户成功");
                }else{
                    return new Message(MessageType.MSG_ERROR, "oauth2","用户不存在");
                }
            }else{
                return new Message(MessageType.MSG_ERROR, "oauth2","用户名为空");
            }
        }catch (Exception ex){
            return new Message(MessageType.MSG_ERROR, "oauth2",ex.getMessage());
        }
    }

}