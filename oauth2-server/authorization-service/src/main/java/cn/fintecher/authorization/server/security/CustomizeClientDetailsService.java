package cn.fintecher.authorization.server.security;

import cn.fintecher.authorization.common.dto.clientdetails.ClientDetailInfo;
import cn.fintecher.authorization.common.dto.roledetails.RoleDetailInfo;
import cn.fintecher.authorization.server.domain.CustomClientDetails;
import cn.fintecher.authorization.server.service.SysClientDetailsService;
import cn.fintecher.common.utils.BeanCopyUtils;
import cn.fintecher.common.utils.basecommon.message.Message;
import cn.fintecher.common.utils.basecommon.message.MessageType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;

import java.util.ArrayList;
import java.util.List;

public class CustomizeClientDetailsService implements ClientDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(CustomizeClientDetailsService.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SysClientDetailsService sysClientDetailsService;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {

        try {
            ResponseEntity<Message> responseEntity = sysClientDetailsService.SearchClientDetail(clientId);
            if (responseEntity == null) {
                String msg = String.format("CustomClientDetailsService SearchClientDetail response is null. %s", clientId);
                throw new ClientRegistrationException(msg);
            }
            if (responseEntity.getBody().getCode()!= MessageType.MSG_SUCCESS) {
                String msg = String.format("CustomClientDetailsService SearchClientDetail response is error. %s", clientId);
                throw new ClientRegistrationException(msg);
            }

            ClientDetailInfo clientDetailInfo = objectMapper.convertValue(responseEntity.getBody().getMessage(), ClientDetailInfo.class);
            if (clientDetailInfo == null) {
                String msg = String.format("CustomClientDetailsService SearchClientDetail convertValue is null. %s", clientId);
                throw new ClientRegistrationException(msg);
            }

            CustomClientDetails customClientDetails = new CustomClientDetails();

            BeanCopyUtils.copyProperties(clientDetailInfo, customClientDetails, true, false, "authorities");
            if (clientDetailInfo.getRoles() != null) {
                List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
                for (RoleDetailInfo roleDetailInfo : clientDetailInfo.getRoles()) {
                    if (roleDetailInfo.getRole() != null) {
                        authorities.add(new SimpleGrantedAuthority(roleDetailInfo.getRole()));
                    }
                }
                customClientDetails.setAuthorities(authorities);
            }

            return customClientDetails;

        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }
}
