package cn.fintecher.common.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;


/**
 * 序列化和反序列化用户权限
 */
public class SerializeTool {
	
	/**
	 * 获取用户角色
	 * @param "role:111;power:aaa; ;power:ccc;function:ddd"
	 * 		  role 角色
	 * 		  power 权限
	 * 		  function 操作
	 * */
	public static RoleSet getRoleSet(String param){
		RoleSet  roleSet = new RoleSet();
		if(param!=null && !"".equals(param) ){
			String[] roleCodes = param.split(";");
			if(roleCodes!=null && roleCodes.length > 0){
				Set<String> roles= new HashSet<String>();
				for (int i = 0; i < roleCodes.length; i++) {
					if(roleCodes[i]!=null && !"".equals(roleCodes[i]) && roleCodes[i].split(":").length>1 && "role".equals(roleCodes[i].split(":")[0])){
						roles.add(roleCodes[i].split(":")[1]);
					} 
				}
				roleSet.setRoles(roles);
			}	
		}	
		return roleSet;
	}
	
	/**
	 * 获取用户权限
	 * @param "role:111;power:aaa; ;power:ccc;function:ddd"
	 * 		  role 角色
	 * 		  power 权限
	 * 		  function 操作
	 * */
	public static PowerSet getPowerSet(String param){
		PowerSet powerSet = new PowerSet();
		if(param!=null && !"".equals(param) ){
			String[] params = param.split(";");
			if(params!=null && params.length > 0){
				Set<String> powers= new HashSet<String>();
				for (int i = 0; i < params.length; i++) {
					if(params[i]!=null && !"".equals(params[i]) && params[i].split(":").length>1 && "power".equals(params[i].split(":")[0])){
						powers.add(params[i].split(":")[1]);
					} 
				}
				powerSet.setPowers(powers);
			}	
		}
		return powerSet;
	}
	
	/**
	 * 获取用户操作
	 * @param "role:111;power:aaa; ;power:ccc;function:ddd"
	 * 		  role 角色
	 * 		  power 权限
	 * 		  function 操作
	 * */
	public static FunctionSet getFunctionSet(String param){
		FunctionSet functionSet = new FunctionSet();
		if(param!=null && !"".equals(param) ){
			String[] params = param.split(";");
			if(params!=null && params.length > 0){
				Set<String> fucntions= new HashSet<String>();
				for (int i = 0; i < params.length; i++) {
					if(params[i]!=null && !"".equals(params[i]) && params[i].split(":").length>1 && "function".equals(params[i].split(":")[0])){
						fucntions.add(params[i].split(":")[1]);
					} 
				}
				functionSet.setFunctions(fucntions);
			}	
		}
		return functionSet;
	}
	
	
	/**
	 * 序列化用户权限 
	 * @param roleSet 角色
	 * 		  powerSet 权限
	 * 		  functionSet 操作
	 * */
	public static String toString(RoleSet roleSet,PowerSet powerSet,FunctionSet functionSet){
		String result = "";
		String regEx = ":;";
        Pattern p = Pattern.compile(regEx);
        
		if(roleSet!=null && roleSet.getRoles()!=null && roleSet.getRoles().size()>0){
			for (String role : roleSet.getRoles()) {
				if(role!=null && !"".equals(role)&&!p.matcher(role).find()) result+="role:"+role+";";
			}
		}
		
		if(powerSet!=null && powerSet.getPowers()!=null && powerSet.getPowers().size()>0){
			for (String power : powerSet.getPowers()) {
				if(power!=null && !"".equals(power)&&!p.matcher(power).find()) result+="power:"+power+";";
			}
		}
		
		if(functionSet!=null && functionSet.getFunctions()!=null && functionSet.getFunctions().size()>0){
			for (String function : functionSet.getFunctions()) {
				if(function!=null && !"".equals(function)&&!p.matcher(function).find()) result+="function:"+function+";";
			}
		}
		
		return result;
	}
	
}
