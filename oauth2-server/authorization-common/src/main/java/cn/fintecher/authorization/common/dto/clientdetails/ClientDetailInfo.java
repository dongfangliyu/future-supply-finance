package cn.fintecher.authorization.common.dto.clientdetails;

import cn.fintecher.authorization.common.dto.roledetails.RoleDetailInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ClientDetailInfo {

    private String clientId;

    private Set<String> resourceIds = null;

    private boolean secretRequired = true;

    private String clientSecret;

    private boolean scoped = true;

    private Set<String> scope = null;

    private Set<String> autoApproveScope = null;

    private Set<String> authorizedGrantTypes = null;

    private Set<String> registeredRedirectUri = null;

    private List<RoleDetailInfo> roles = null;

    private Integer accessTokenValiditySeconds = null;

    private Integer refreshTokenValiditySeconds = null;

    private Map<String, Object> additionalInformation = null;

    public List<RoleDetailInfo> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDetailInfo> roles) {
        this.roles = roles;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Set<String> getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(Set<String> resourceIds) {
        this.resourceIds = resourceIds;
    }

    public boolean isSecretRequired() {
        return secretRequired;
    }

    public void setSecretRequired(boolean secretRequired) {
        this.secretRequired = secretRequired;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public boolean isScoped() {
        return scoped;
    }

    public void setScoped(boolean scoped) {
        this.scoped = scoped;
    }

    public Set<String> getScope() {
        return scope;
    }

    public void setScope(Set<String> scope) {
        this.scope = scope;
    }

    public Set<String> getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    public void setAuthorizedGrantTypes(Set<String> authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }

    public Set<String> getRegisteredRedirectUri() {
        return registeredRedirectUri;
    }

    public void setRegisteredRedirectUri(Set<String> registeredRedirectUri) {
        this.registeredRedirectUri = registeredRedirectUri;
    }

    public Integer getAccessTokenValiditySeconds() {
        return accessTokenValiditySeconds;
    }

    public void setAccessTokenValiditySeconds(Integer accessTokenValiditySeconds) {
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
    }

    public Integer getRefreshTokenValiditySeconds() {
        return refreshTokenValiditySeconds;
    }

    public void setRefreshTokenValiditySeconds(Integer refreshTokenValiditySeconds) {
        this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
    }

    public boolean isAutoApprove(String scope) {

        if (getAutoApproveScope() != null && scope != null) {
            if (getAutoApproveScope().contains(scope) && scope.contains(scope)) {
                return true;
            }
        }
        return false;
    }

    public void setAutoApprove(Set<String> autoApprove) {
        this.setAutoApproveScope(autoApprove);
    }

    public Map<String, Object> getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(Map<String, Object> additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public Set<String> getAutoApproveScope() {
        return autoApproveScope;
    }

    public void setAutoApproveScope(Set<String> autoApproveScope) {
        this.autoApproveScope = autoApproveScope;
    }
}
