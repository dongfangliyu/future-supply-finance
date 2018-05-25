package cn.fintecher.authorization.agent.service;


import cn.fintecher.authorization.agent.dao.SysUserRoleDao;
import cn.fintecher.authorization.common.dto.functionDetails.FunctionDetailInfo;
import cn.fintecher.authorization.common.dto.roledetails.RoleDetailInfo;
import cn.fintecher.authorization.conf.provider.function.ResourceDefaultUserFunction;
import cn.fintecher.authorization.conf.provider.function.ResourceFunctionService;
import cn.fintecher.authorization.conf.provider.function.ResourceUserFunction;
import cn.fintecher.authorization.conf.provider.function.ResourceUserFunctionException;
import cn.fintecher.authorization.conf.provider.role.ResourceDefaultUserRole;
import cn.fintecher.authorization.conf.provider.role.ResourceRoleService;
import cn.fintecher.authorization.conf.provider.role.ResourceUserRole;
import cn.fintecher.authorization.conf.provider.role.ResourceUserRoleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomizeResourceRoleService implements ResourceRoleService,ResourceFunctionService {

	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	
    public ResourceUserRole loadRoleByUsername(String userName) throws ResourceUserRoleException {

        ResourceUserRole userRole = new ResourceDefaultUserRole();

        List<RoleDetailInfo> roles = new ArrayList<RoleDetailInfo>();

       /* RoleDetailInfo roleInfo1 = new RoleDetailInfo();

        roleInfo1.setRole("ROLE_USER");

        roles.add(roleInfo1);*/

        List<String> roleCodes = sysUserRoleDao.findRolesByUsername(userName);
        if(roleCodes!=null && roleCodes.size()>0){
        	for (String roleCode : roleCodes) {
        		RoleDetailInfo roleInfo = new RoleDetailInfo();
                roleInfo.setRole(roleCode);
                roles.add(roleInfo);
			}
        }
        
        /*RoleDetailInfo roleInfo2 = new RoleDetailInfo();

        roleInfo2.setRole("aaa");

        roles.add(roleInfo2);*/

        userRole.setRoles(roles);

        return userRole;
    }

	public ResourceUserFunction loadFunctionByUsername(String userName)
			throws ResourceUserFunctionException {
		ResourceUserFunction userFunction = new ResourceDefaultUserFunction();
		List<FunctionDetailInfo> functions = new ArrayList<FunctionDetailInfo>();
		
		 List<String> functionCodes = sysUserRoleDao.findFunctionByUsername(userName);
	        if(functionCodes!=null && functionCodes.size()>0){
	        	for (String functionCode : functionCodes) {
	        		if(functionCode!=null && !"".equals(functionCode) && functionCode.split(",").length>0){
	        			for (String function:functionCode.split(",")) {
	        				FunctionDetailInfo functionDetailInfo = new FunctionDetailInfo();
			        		functionDetailInfo.setFunction(function);
			        		functions.add(functionDetailInfo);
						}
	        		}
	        		
				}
	        }
        userFunction.setFunctions(functions);
		return userFunction;
	}

}
