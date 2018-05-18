package cn.fintecher.authorization.conf.provider.role;

public class ResourceUserRoleException extends RuntimeException {

    public ResourceUserRoleException(String msg) {
        super(msg);
    }

    public ResourceUserRoleException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
