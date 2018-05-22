package cn.fintecher.authorization.agent.service;

import cn.fintecher.authorization.common.dto.roledetails.RoleDetailInfo;
import cn.fintecher.authorization.common.dto.userdetails.UserDetailInfo;
import cn.fintecher.common.utils.basecommon.message.Message;
import cn.fintecher.common.utils.basecommon.message.MessageType;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserInfoService {

    private static final Logger LOG = LoggerFactory.getLogger(UserInfoService.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SysUserDetailsService sysUserDetailsService;

    public Map<String, String> user(Principal principal) {
        Map<String, String> map = new LinkedHashMap<>();

        try {
            UserDetailInfo userDetailInfo = loadUserByUsername(principal.getName());

           /* map.put("name", String.format("%s %s"
                    , userDetailInfo.getFirstName()
                    , userDetailInfo.getLastName()));*/
            map.put("name", userDetailInfo.getUsername());
        } catch (UsernameNotFoundException ex) {
            map.put("name", principal.getName());
        }

        return map;
    }

    public String account(Principal principal)
            throws Exception {

        JSONObject jsonObject = new JSONObject();

        try {
            UserDetailInfo userDetailInfo = loadUserByUsername(principal.getName());

            /*userInfo = new UserInfo(userDetailInfo.getFirstName()
                    , userDetailInfo.getLastName());*/
            jsonObject.put("name", userDetailInfo.getUsername()); 
            String roleString = "";
            List<RoleDetailInfo> roles = userDetailInfo.getRoles();
            if(roles!=null && roles.size()>0){
            	for (RoleDetailInfo roleDetailInfo : roles) {
            		roleString += roleDetailInfo.getRole()+",";
    			}
            }
            jsonObject.put("roles", roleString);
        } catch (UsernameNotFoundException ex) {
        	jsonObject.put("name", principal.getName());  
        }
        return jsonObject.toString();
    }

    private UserDetailInfo loadUserByUsername(String username) throws UsernameNotFoundException {

        try {
            ResponseEntity<Message> responseEntity = sysUserDetailsService.SearchUserDetail(username);
            if (responseEntity == null) {
                String msg = String.format("SysUserDetailsService SearchUserDetail response is null. %s", username);
                throw new UsernameNotFoundException(msg);
            }

            if (responseEntity.getBody().getCode()!= MessageType.MSG_SUCCESS) {
                String msg = String.format("SysUserDetailsService SearchUserDetail response is error. %s", username);
                throw new UsernameNotFoundException(msg);
            }

            UserDetailInfo userDetailInfo = objectMapper.convertValue(responseEntity.getBody().getMessage(), UserDetailInfo.class);
            if (userDetailInfo == null) {
                String msg = String.format("SysUserDetailsService SearchUserDetail convertValue is null. %s", username);
                throw new UsernameNotFoundException(msg);
            }

            return userDetailInfo;

        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }
}
