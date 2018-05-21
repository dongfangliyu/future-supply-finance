package cn.fintecher.authorization.conf.provider;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.WebSecurityExpressionRoot;

public class CustomWebSecurityExpressionRoot extends WebSecurityExpressionRoot {

    public CustomWebSecurityExpressionRoot(Authentication a, FilterInvocation fi) {
        super(a, fi);
    }


    public boolean hasPermission(String permission) {

        authentication.getAuthorities();
        return true;
    }

    public boolean hasAnyPermission(String... permissions) {
        return true;
    }
}
