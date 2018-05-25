package cn.fintecher.authorization.manager.clientdetails.service;


import cn.fintecher.authorization.common.dto.clientdetails.ClientDetailInfo;
import cn.fintecher.authorization.common.dto.roledetails.RoleDetailInfo;
import cn.fintecher.common.utils.basecommon.message.Message;
import cn.fintecher.common.utils.basecommon.message.MessageType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SysClientDetailsService {

    public ResponseEntity<Message> SearchClientDetail(String clientId) {

        System.out.println("== SearchClientDetail ==");
//                .withClient("my-trusted-client-with-secret")
//                .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
//                .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
//                .scopes("read", "write", "trust")
//                .secret("somesecret")

        ClientDetailInfo clientDetailInfo = new ClientDetailInfo();

        clientDetailInfo.setClientId("my-trusted-client-with-secret");
        clientDetailInfo.setResourceIds(null);
        clientDetailInfo.setSecretRequired(true);
        clientDetailInfo.setClientSecret("somesecret");
        clientDetailInfo.setScoped(true);

        Set<String> scopes = new HashSet<String>();
        scopes.add("read");
        scopes.add("write");
        scopes.add("trust");
        clientDetailInfo.setScope(scopes);

        Set<String> authorizedGrantTypes = new HashSet<String>();
        authorizedGrantTypes.add("password");
        authorizedGrantTypes.add("authorization_code");
        authorizedGrantTypes.add("refresh_token");
        authorizedGrantTypes.add("implicit");
        clientDetailInfo.setAuthorizedGrantTypes(authorizedGrantTypes);

        clientDetailInfo.setRegisteredRedirectUri(null);

        List<RoleDetailInfo> roles = new ArrayList<RoleDetailInfo>();

        RoleDetailInfo roleInfo1 = new RoleDetailInfo();
        roleInfo1.setRole("ROLE_CLIENT");
        roles.add(roleInfo1);

        RoleDetailInfo roleInfo2 = new RoleDetailInfo();
        roleInfo2.setRole("ROLE_TRUSTED_CLIENT");
        roles.add(roleInfo2);

        clientDetailInfo.setRoles(roles);

        clientDetailInfo.setRegisteredRedirectUri(null);

        clientDetailInfo.setRefreshTokenValiditySeconds(null);

        clientDetailInfo.setAutoApprove(null);

        clientDetailInfo.setAdditionalInformation(null);

        return new ResponseEntity<Message>(new Message(MessageType.MSG_SUCCESS,"oauth2", clientDetailInfo), HttpStatus.OK);
    }

}
