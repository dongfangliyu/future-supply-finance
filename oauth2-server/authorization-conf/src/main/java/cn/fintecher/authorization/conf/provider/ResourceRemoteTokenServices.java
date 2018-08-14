package cn.fintecher.authorization.conf.provider;


import cn.fintecher.authorization.common.dto.functionDetails.FunctionDetailInfo;
import cn.fintecher.authorization.common.dto.roledetails.RoleDetailInfo;
import cn.fintecher.authorization.conf.provider.function.ResourceFunctionService;
import cn.fintecher.authorization.conf.provider.function.ResourceUserFunction;
import cn.fintecher.authorization.conf.provider.role.ResourceRoleService;
import cn.fintecher.authorization.conf.provider.role.ResourceUserRole;
import cn.fintecher.authorization.conf.provider.role.ResourceUserRoleException;
import cn.fintecher.common.utils.FunctionSet;
import cn.fintecher.common.utils.RoleSet;
import cn.fintecher.common.utils.SerializeTool;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.security.crypto.codec.Base64;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.*;

public class ResourceRemoteTokenServices implements ResourceServerTokenServices {

    protected final Log logger = LogFactory.getLog(getClass());

    private RestOperations restTemplate;

    private String checkTokenEndpointUrl;

    private String clientId;

    private String clientSecret;

    private String tokenName = "token";

    private AccessTokenConverter tokenConverter = new DefaultAccessTokenConverter();

    private ResourceRoleService resourceRoleService;
    
    private ResourceFunctionService resourceFunctionService;

    final String AUTHORITIES = AccessTokenConverter.AUTHORITIES;

    final String USERNAME = "user_name";

    public ResourceRemoteTokenServices() {
        restTemplate = new RestTemplate();
        ((RestTemplate) restTemplate).setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            // Ignore 400
            public void handleError(ClientHttpResponse response) throws IOException {
                if (response.getRawStatusCode() != 400) {
                    super.handleError(response);
                }
            }
        });
    }

    public void setResourceFunctionService(ResourceFunctionService resourceFunctionService) {
		this.resourceFunctionService = resourceFunctionService;
	}

	public void setResourceRoleService(ResourceRoleService resourceRoleService) {
        this.resourceRoleService = resourceRoleService;
    }

    public void setRestTemplate(RestOperations restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void setCheckTokenEndpointUrl(String checkTokenEndpointUrl) {
        this.checkTokenEndpointUrl = checkTokenEndpointUrl;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public void setAccessTokenConverter(AccessTokenConverter accessTokenConverter) {
        this.tokenConverter = accessTokenConverter;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    @Override
    public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<String, String>();
        formData.add(tokenName, accessToken);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", getAuthorizationHeader(clientId, clientSecret));
        Map<String, Object> map = postForMap(checkTokenEndpointUrl, formData, headers);

        if (map.containsKey("error")) {
            logger.debug("check_token returned error: " + map.get("error"));
            throw new InvalidTokenException(accessToken);
        }

        // gh-838
        if (!Boolean.TRUE.equals(map.get("active"))) {
            logger.debug("check_token returned active attribute: " + map.get("active"));
            throw new InvalidTokenException(accessToken);
        }

        loadResouceAuthorities(map);

        return tokenConverter.extractAuthentication(map);
    }

    @Override
    public OAuth2AccessToken readAccessToken(String accessToken) {
        throw new UnsupportedOperationException("Not supported: read access token");
    }

    private String getAuthorizationHeader(String clientId, String clientSecret) {

        if (clientId == null || clientSecret == null) {
            logger.warn("Null Client ID or Client Secret detected. Endpoint that requires authentication will reject request with 401 error.");
        }

        String creds = String.format("%s:%s", clientId, clientSecret);
        try {
            return "Basic " + new String(Base64.encode(creds.getBytes("UTF-8")));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("Could not convert String");
        }
    }

    private Map<String, Object> postForMap(String path, MultiValueMap<String, String> formData, HttpHeaders headers) {
        if (headers.getContentType() == null) {
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        }
        @SuppressWarnings("rawtypes")
        Map map = restTemplate.exchange(path, HttpMethod.POST,
                new HttpEntity<MultiValueMap<String, String>>(formData, headers), Map.class).getBody();
        @SuppressWarnings("unchecked")
        Map<String, Object> result = map;
        return result;
    }
    
   //For Load Resource Service User Authorities
    @SuppressWarnings("unchecked")
	private void loadResouceAuthorities(Map<String, Object> map) {
    	
        if (!map.containsKey(USERNAME) || !map.containsKey(AUTHORITIES))return;
  
        Object userName = map.get(USERNAME);
        if (!(userName instanceof String)) return;

        Object authorities = map.get(AUTHORITIES);
        if (!(authorities instanceof Collection))return;
        
        try {
        	List<String> newAuthorities = new ArrayList<String>();
        	Set<String> roles = new HashSet<String>();
        	Set<String> functions = new HashSet<String>();
        	if(authorities!=null){
        		List<String> oldAuthorities = (List<String>) authorities;
        		if(oldAuthorities!=null && oldAuthorities.size()>0){
        			String param = oldAuthorities.get(0);
        			RoleSet roleSet= SerializeTool.getRoleSet(param);
        			FunctionSet functionSet = SerializeTool.getFunctionSet(param);
        			if(roleSet!=null) roles = roleSet.getRoles();
        			if(functionSet!=null) functions = functionSet.getFunctions();
        		}
        	}
        	
//            newAuthorities.addAll((Collection<String>) authorities);
             
        	if (resourceRoleService != null){
        		ResourceUserRole userRole = resourceRoleService.loadRoleByUsername((String) userName);
                if(userRole!=null && userRole.getRoles().size()>0){
                	for (RoleDetailInfo roleDetailInfo : userRole.getRoles()) {
		                if (roleDetailInfo.getRole() != null) {
		                	roles.add(roleDetailInfo.getRole());
		                }
                    }
                }
        	}
        	
        	if(resourceFunctionService!=null){
        		ResourceUserFunction userFunction = resourceFunctionService.loadFunctionByUsername((String) userName);
        		if(userFunction!=null && userFunction.getFunctions().size()>0){
                	for (FunctionDetailInfo functionDetailInfo : userFunction.getFunctions()) {
		                if (functionDetailInfo.getFunction() != null) {
		                	functions.add(functionDetailInfo.getFunction());
		                }
                    }
                }
        	}
        	
            /*removeDuplicate((ArrayList) newAuthorities);*/
        	newAuthorities.add(SerializeTool.toString(new RoleSet(roles), null, new FunctionSet(functions)));
            map.remove(AUTHORITIES);
            map.put(AUTHORITIES, newAuthorities);
            
        } catch (ResourceUserRoleException e) {
            throw new OAuth2AccessDeniedException("Load Resource User Role Failed.");
        }
    }
    //For Load Resource Service User Role
   /* private void loadResouceAuthorities(Map<String, Object> map) {
        if (resourceRoleService == null)
            return;

        if (!map.containsKey(USERNAME) || !map.containsKey(AUTHORITIES))
            return;

        Object userName = map.get(USERNAME);
        if (!(userName instanceof String)) {
            return;
        }

        Object authorities = map.get(AUTHORITIES);
        if (!(authorities instanceof Collection)) {
            return;
        }

        try {

            ResourceUserRole userRole = resourceRoleService.loadRoleByUsername((String) userName);
            if (userRole == null) {
                return;
            }

            List<String> newAuthorities = new ArrayList<String>();

            newAuthorities.addAll((Collection<String>) authorities);

            for (RoleDetailInfo roleDetailInfo : userRole.getRoles()) {
                if (roleDetailInfo.getRole() != null) {
                    newAuthorities.add(roleDetailInfo.getRole());
                }
            }

            removeDuplicate((ArrayList) newAuthorities);

            map.remove(AUTHORITIES);
            map.put(AUTHORITIES, newAuthorities);

        } catch (ResourceUserRoleException e) {
            throw new OAuth2AccessDeniedException("Load Resource User Role Failed.");
        }
    }*/

    private void removeDuplicate(ArrayList arlList) {
        HashSet h = new HashSet(arlList);
        arlList.clear();
        arlList.addAll(h);
    }
}
