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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysUserDetailsService {

	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	
	@Autowired
	private SysUserDao sysUserDao;
	
    public ResponseEntity<Message> SearchUserDetail(String username) {

        System.out.println("== SearchUserDetail ==");

        UserDetailInfo userDetailInfo = new UserDetailInfo();
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        
        SysUser sysUser = sysUserDao.getByUserName(username);
        
        if(sysUser!=null){
        	userDetailInfo.setUsername(sysUser.getUsername());
        	userDetailInfo.setPassword(encoder.encode(sysUser.getPassword()));
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
        
//        if (username.equals("test")) {
//            userDetailInfo.setUsername("test");
//            userDetailInfo.setPassword(encoder.encode("test"));
//            userDetailInfo.setFirstName("test");
//            userDetailInfo.setLastName("001");
//        } else {
//            userDetailInfo.setUsername("paul");
//            userDetailInfo.setPassword(encoder.encode("emu"));
//            userDetailInfo.setFirstName("paul");
//            userDetailInfo.setLastName("emu");
//        }

        return new ResponseEntity<Message>(new Message(MessageType.MSG_SUCCESS,"oauth2", userDetailInfo), HttpStatus.OK);

    }
}