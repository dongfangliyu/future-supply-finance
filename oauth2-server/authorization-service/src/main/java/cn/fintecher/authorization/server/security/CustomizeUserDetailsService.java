package cn.fintecher.authorization.server.security;

import cn.fintecher.authorization.common.dto.functionDetails.FunctionDetailInfo;
import cn.fintecher.authorization.common.dto.roledetails.RoleDetailInfo;
import cn.fintecher.authorization.common.dto.userdetails.UserDetailInfo;
import cn.fintecher.authorization.server.domain.CustomUserDetails;
import cn.fintecher.authorization.server.service.SysUserDetailsService;
import cn.fintecher.common.utils.BeanCopyUtils;
import cn.fintecher.common.utils.FunctionSet;
import cn.fintecher.common.utils.RoleSet;
import cn.fintecher.common.utils.SerializeTool;
import cn.fintecher.common.utils.basecommon.message.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomizeUserDetailsService implements UserDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(CustomizeUserDetailsService.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SysUserDetailsService sysUserDetailsService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try {
            ResponseEntity<Message> responseEntity = sysUserDetailsService.SearchUserDetail(username);
            if (responseEntity == null) {
                String msg = String.format("SysUserDetailsService SearchUserDetail response is null. %s", username);
                throw new UsernameNotFoundException(msg);
            }

            if (responseEntity.getBody().getStatus().endsWith("ERROR")) {
                String msg = String.format("SysUserDetailsService SearchUserDetail response is error. %s", username);
                throw new UsernameNotFoundException(msg);
            }

            UserDetailInfo userDetailInfo = objectMapper.convertValue(responseEntity.getBody().getData(), UserDetailInfo.class);
            
            if (userDetailInfo == null) {
                String msg = String.format("SysUserDetailsService SearchUserDetail convertValue is null. %s", username);
                throw new UsernameNotFoundException(msg);
            }

            CustomUserDetails customUserDetails = new CustomUserDetails();

            BeanCopyUtils.copyProperties(userDetailInfo, customUserDetails, true, false, "authorities");
            /*if (userDetailInfo.getRoles() != null) {
                List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
                for (RoleDetailInfo roleDetailInfo : userDetailInfo.getRoles()) {
                    if (roleDetailInfo.getRole() != null) {
                        authorities.add(new SimpleGrantedAuthority(roleDetailInfo.getRole()));
                    }
                }
                customUserDetails.setAuthorities(authorities);
            }*/
			
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            Set<String> roles = new HashSet<String>();
            Set<String> functions = new HashSet<String>();
            if (userDetailInfo.getRoles() != null) {
                for (RoleDetailInfo roleDetailInfo : userDetailInfo.getRoles()) {
                    if (roleDetailInfo.getRole() != null)roles.add(roleDetailInfo.getRole()); 
                }
            }
            
            if (userDetailInfo.getFunctions() != null) {
                for (FunctionDetailInfo functionDetailInfo: userDetailInfo.getFunctions()) {
                    if (functionDetailInfo.getFunction() != null)functions.add(functionDetailInfo.getFunction()); 
                }
            }
            
            authorities.add(new SimpleGrantedAuthority(SerializeTool.toString(new RoleSet(roles), null, new FunctionSet(functions))));
            customUserDetails.setAuthorities(authorities);
            
            return customUserDetails;

        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }
}
