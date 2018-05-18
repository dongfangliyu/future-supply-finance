package cn.fintecher.authorization.conf.provider;

import cn.fintecher.common.utils.FunctionSet;
import cn.fintecher.common.utils.SerializeTool;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;

import java.util.HashSet;
import java.util.Set;

public class FunctionSecurityExpressionMethods {
    private final Authentication authentication;
    private final FilterInvocation invocation;
    private final Set<String> functions;
    public FunctionSecurityExpressionMethods(Authentication authentication,FilterInvocation invocation) {
        this.authentication = authentication;
        this.invocation = invocation;
        Set<String> functions = new HashSet<String>();
        for (GrantedAuthority authority : authentication.getAuthorities()) {
        	FunctionSet functionSet = SerializeTool.getFunctionSet(authority.getAuthority());
        	if(functionSet!=null && functionSet.getFunctions()!=null){
        		functions.addAll(functionSet.getFunctions());
        	}
        }
        
        this.functions = functions;
    }

    public boolean hasFunction(String function) {
		if(function!=null && !"".equals(function) && this.functions!=null && this.functions.size()>0){
			for (String functionCode : functions) {
				if(functionCode!=null &&function.equals(functionCode) ){
					return true;
				}
			}
		}
		return false;
    }

    public boolean hasAnyFunction(String... functions) {
    	if(functions!=null && functions.length>0 && this.functions!=null &&this.functions.size()>0){
    		for (String function:functions) {
				for (String functionCode : functions) {
					if(function!=null && functionCode!=null && function.equals(functionCode) ){
						return true;
					}
				}
			}
    	}
    	return false;
    }
}
