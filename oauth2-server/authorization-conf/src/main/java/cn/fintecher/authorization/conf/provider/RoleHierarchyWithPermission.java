package cn.fintecher.authorization.conf.provider;

import cn.fintecher.common.utils.SerializeTool;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;


public class RoleHierarchyWithPermission implements RoleHierarchy {
    @Override
    public Collection<GrantedAuthority> getReachableGrantedAuthorities(Collection<? extends GrantedAuthority> authorities) {
        if (authorities == null || authorities.isEmpty()) {
            return AuthorityUtils.NO_AUTHORITIES;
        }

        Set<GrantedAuthority> reachableRoles = new HashSet<GrantedAuthority>();

        for (GrantedAuthority authority : authorities) {
//            addReachableRoles(reachableRoles, authority);
//            Set<GrantedAuthority> additionalReachableRoles = getRolesReachableInOneOrMoreSteps(
//                    authority);
//            if (additionalReachableRoles != null) {
//                reachableRoles.addAll(additionalReachableRoles);
//            }
/*
            String[] array = authority.getAuthority().split(":");

            if(array == null || array.length == 0){
                continue;
            }

            if(array[0].isEmpty()){
                continue;
            }*/
        	Set<String> roles = SerializeTool.getRoleSet(authority.getAuthority()).getRoles();
        	
        	for (String role:roles) {
        		reachableRoles.add(new SimpleGrantedAuthority(role));
			}
        }

        List<GrantedAuthority> reachableRoleList = new ArrayList<GrantedAuthority>(reachableRoles.size());
        
        reachableRoleList.addAll(reachableRoles);

        return reachableRoleList;
    }
}
