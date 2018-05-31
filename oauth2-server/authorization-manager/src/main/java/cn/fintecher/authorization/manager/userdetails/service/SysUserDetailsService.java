package cn.fintecher.authorization.manager.userdetails.service;


import cn.fintecher.authorization.common.dto.roledetails.RoleDetailInfo;
import cn.fintecher.authorization.common.dto.userdetails.UserDetailInfo;
import cn.fintecher.authorization.manager.userdetails.dao.SysUserDao;
import cn.fintecher.authorization.manager.userdetails.dao.SysUserRoleDao;
import cn.fintecher.authorization.manager.userdetails.entity.SysUser;
import cn.fintecher.common.utils.basecommon.message.Message;
import cn.fintecher.common.utils.basecommon.message.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SysUserDetailsService {

	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	
	@Autowired
	private SysUserDao sysUserDao;
	
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

    public ResponseEntity<Message>  createUser(Map<String,Object> map){
        try {
            if(map.containsKey("username")&& map.get("username")!=null && !"".equals(map.get("username"))){
                if(map.containsKey("password")&& map.get("password")!=null && !"".equals(map.get("password"))){
                    SysUser sysUser = sysUserDao.getByUserName(map.get("username").toString());
                    if(sysUser!=null && sysUser.getId()>0){
                        return new ResponseEntity<Message>(new Message(MessageType.MSG_ERROR, "oauth2","用户已存在"), HttpStatus.OK);
                    }else{
                        sysUser.setCreateTime(new Date());
                        if(map.containsKey("name"))sysUser.setName(map.get("name").toString());
                        sysUser.setStauts(1);
                        sysUser.setUsername(map.get("username").toString());
                        sysUser.setPassword(map.get("password").toString());
                        sysUserDao.createUser(sysUser);
                        return  new ResponseEntity<Message>(new Message(MessageType.MSG_SUCCESS, "oauth2","新增用户成功"), HttpStatus.OK);
                    }
                }else{
                    return new ResponseEntity<Message>(new Message(MessageType.MSG_ERROR, "oauth2","密码为空"), HttpStatus.OK);
                }
            }else{
                return new ResponseEntity<Message>(new Message(MessageType.MSG_ERROR, "oauth2","用户名为空"), HttpStatus.OK);
            }
        }catch (Exception ex){
            return new ResponseEntity<Message>(new Message(MessageType.MSG_ERROR, "oauth2",ex.getMessage()), HttpStatus.OK);
        }

    }

    public ResponseEntity<Message>  updateUser(Map<String,Object> map){
        try {
            if(map.containsKey("username")&& map.get("username")!=null && !"".equals(map.get("username"))){
                SysUser sysUser = sysUserDao.getByUserName(map.get("username").toString());
                if(sysUser!=null && sysUser.getId()>0){
                    if(map.containsKey("name"))sysUser.setName(map.get("name").toString());
                    sysUser.setStauts(1);
                    sysUser.setUsername(map.get("username").toString());
                    if(map.containsKey("password"))sysUser.setPassword(map.get("password").toString());
                    sysUserDao.updateUser(sysUser);
                    return  new ResponseEntity<Message>(new Message(MessageType.MSG_SUCCESS, "oauth2","修改用户成功"), HttpStatus.OK);
                }else{
                    return new ResponseEntity<Message>(new Message(MessageType.MSG_ERROR, "oauth2","用户不存在"), HttpStatus.OK);
                }
            }else{
                return new ResponseEntity<Message>(new Message(MessageType.MSG_ERROR, "oauth2","用户名为空"), HttpStatus.OK);
            }
        }catch (Exception ex){
            return new ResponseEntity<Message>(new Message(MessageType.MSG_ERROR, "oauth2",ex.getMessage()), HttpStatus.OK);
        }
    }

}