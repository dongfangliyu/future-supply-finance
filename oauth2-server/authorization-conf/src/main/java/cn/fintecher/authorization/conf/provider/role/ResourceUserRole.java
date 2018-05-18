package cn.fintecher.authorization.conf.provider.role;


import cn.fintecher.authorization.common.dto.roledetails.RoleDetailInfo;

import java.util.List;

public interface ResourceUserRole {

    List<RoleDetailInfo> getRoles();

    void setRoles(List<RoleDetailInfo> roles);
}
