package cn.fintecher.authorization.conf.provider.role;

public interface ResourceRoleService {

    ResourceUserRole loadRoleByUsername(String userName) throws ResourceUserRoleException;
}
