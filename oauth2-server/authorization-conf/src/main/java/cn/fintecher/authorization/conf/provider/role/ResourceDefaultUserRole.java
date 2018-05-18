package cn.fintecher.authorization.conf.provider.role;


import cn.fintecher.authorization.common.dto.roledetails.RoleDetailInfo;

import java.util.List;

public class ResourceDefaultUserRole implements ResourceUserRole {

    private List<RoleDetailInfo> roles = null;

    public List<RoleDetailInfo> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDetailInfo> roles) {
        this.roles = roles;
    }
}
