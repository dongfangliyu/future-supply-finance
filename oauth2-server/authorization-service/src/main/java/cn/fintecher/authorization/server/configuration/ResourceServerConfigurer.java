package cn.fintecher.authorization.server.configuration;

import cn.fintecher.authorization.conf.configuration.ResourceServerProperties;
import cn.fintecher.authorization.conf.provider.BearerAndCookieTokenExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;

@Configuration
@EnableResourceServer
@EnableConfigurationProperties({ResourceServerProperties.class})
public class ResourceServerConfigurer extends ResourceServerConfigurerAdapter {

    @Autowired
    private ResourceServerProperties resourceServerProperties;

    @Bean
    public TokenExtractor tokenExtractor() {
        return new BearerAndCookieTokenExtractor();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.tokenExtractor(tokenExtractor())
                .resourceId(resourceServerProperties.getResourceId()).stateless(false);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
//                // Since we want the protected resources to be accessible in the UI as well we need
//                // session creation to be allowed (it's disabled by default in 2.0.6)
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//                .and()
                .requestMatchers().antMatchers("/oauth/revoke_token/**"
                , "/oauth/users/**"
                , "/oauth/clients/**"
                , "/oauth/v1/**"
                , "/oauth/v2/**")
                .and()
                .authorizeRequests()
                .antMatchers("/oauth/v1")
                .access("#oauth2.hasScope('read')")
                .antMatchers("/oauth/v2")
                .access("#oauth2.hasScope('read')")
                .antMatchers("/oauth/authorize")
                .access("#oauth2.hasScope('read')")
                .antMatchers(HttpMethod.DELETE, "/oauth/revoke_token/**")
                .access("#oauth2.clientHasRole('ROLE_CLIENT') and (hasRole('ROLE_USER') or #oauth2.isClient()) and #oauth2.hasScope('write')")
                .regexMatchers(HttpMethod.DELETE, "/oauth/users/([^/].*?)/tokens/.*")
                .access("#oauth2.clientHasRole('ROLE_CLIENT') and (hasRole('ROLE_USER') or #oauth2.isClient()) and #oauth2.hasScope('write')")
                .regexMatchers(HttpMethod.GET, "/oauth/clients/([^/].*?)/users/.*")
                .access("#oauth2.clientHasRole('ROLE_CLIENT') and (hasRole('ROLE_USER') or #oauth2.isClient()) and #oauth2.hasScope('read')")
                .regexMatchers(HttpMethod.GET, "/oauth/clients/.*")
                .access("#oauth2.clientHasRole('ROLE_CLIENT') and #oauth2.isClient() and #oauth2.hasScope('read')");
        // @formatter:on
    }
}
