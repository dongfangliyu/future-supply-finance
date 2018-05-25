package cn.fintecher.authorization.agent.configuration;


import cn.fintecher.authorization.agent.service.CustomizeResourceRoleService;
import cn.fintecher.authorization.conf.configuration.RemoteTokenServicesProperties;
import cn.fintecher.authorization.conf.configuration.ResourceServerProperties;
import cn.fintecher.authorization.conf.provider.BearerAndCookieTokenExtractor;
import cn.fintecher.authorization.conf.provider.CustomOAuth2WebSecurityExpressionHandler;
import cn.fintecher.authorization.conf.provider.ResourceRemoteTokenServices;
import cn.fintecher.authorization.conf.provider.RoleHierarchyWithPermission;
import cn.fintecher.authorization.conf.provider.function.ResourceFunctionService;
import cn.fintecher.authorization.conf.provider.role.ResourceRoleService;
import cn.fintecher.common.utils.confcommon.filter.EnableCorsFilterConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.security.oauth2.provider.expression.OAuth2SecurityExpressionMethods;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.web.FilterInvocation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.filter.CorsFilter;

@Configuration
@CrossOrigin
@EnableCorsFilterConfigurer
@EnableResourceServer
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties({ResourceServerProperties.class, RemoteTokenServicesProperties.class})
public class ResourceServerConfigurer extends ResourceServerConfigurerAdapter {

    @Autowired
    private CorsFilter corsFilter;

    @Autowired
    private ResourceServerProperties resourceServerProperties;

    @Autowired
    private RemoteTokenServicesProperties remoteTokenServicesProperties;

    @Bean
    public TokenExtractor tokenExtractor() {
        return new BearerAndCookieTokenExtractor();
    }

    @Bean
    public ResourceRoleService resourceRoleService() {
        return new CustomizeResourceRoleService();
    }
    
    @Bean
    public ResourceFunctionService resourceFunctionService() {
        return new CustomizeResourceRoleService();
    }

    @Bean
    public SecurityExpressionHandler<FilterInvocation> expressionHandler()
    {
        CustomOAuth2WebSecurityExpressionHandler handler = new CustomOAuth2WebSecurityExpressionHandler();
        handler.setRoleHierarchy(new RoleHierarchyWithPermission());
        return handler;
        // OAuth2WebSecurityExpressionHandler handler = new OAuth2WebSecurityExpressionHandler();
    //   handler.setRoleHierarchy(new RoleHierarchyWithPermission());

     //   handler.setPermissionEvaluator(new PermissionEvaluatorWithRole());

     //   return handler;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.authenticationManager(authenticationManager())
                .expressionHandler(expressionHandler())
                .tokenExtractor(tokenExtractor())
                .resourceId(resourceServerProperties.getResourceId()).stateless(false);
    }

    @Bean
    public AuthenticationManager authenticationManager() {

      //  SecurityExpressionRoot
        final OAuth2AuthenticationManager oAuth2AuthenticationManager = new OAuth2AuthenticationManager();
        oAuth2AuthenticationManager.setTokenServices(tokenService());
        return oAuth2AuthenticationManager;
    }

    @Bean
    public AccessTokenConverter accessTokenConverter() {
        return new DefaultAccessTokenConverter();
    }

    @Bean
    public ResourceServerTokenServices tokenService() {
        //RemoteTokenServices tokenServices = new RemoteTokenServices();
        ResourceRemoteTokenServices tokenServices = new ResourceRemoteTokenServices();

        //For Load Resource Role
        tokenServices.setResourceRoleService(resourceRoleService());
        
        //For Load Resource function
        tokenServices.setResourceFunctionService(resourceFunctionService());

        tokenServices.setClientId(remoteTokenServicesProperties.getClientId());
        tokenServices.setClientSecret(remoteTokenServicesProperties.getClientSecret());
        tokenServices.setTokenName(remoteTokenServicesProperties.getTokenName());

        tokenServices.setCheckTokenEndpointUrl(remoteTokenServicesProperties.getCheckTokenEndpointUrl());
        tokenServices.setAccessTokenConverter(accessTokenConverter());
        return tokenServices;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

//                       .requestMatchers().antMatchers("/oauth/revoke_token/**"
//                , "/oauth/users/**"
//                , "/oauth/clients/**"
//                ,"/oauth/v1/**"
//                ,"/oauth/v2/**")
//                .and()
//                .authorizeRequests()
//                .antMatchers("/oauth/v1")
//                .access("#oauth2.hasScope('read')")
//                .antMatchers("/oauth/v2")
//                .access("#oauth2.hasScope('read')")
//                .antMatchers("/oauth/authorize")
//                .access("#oauth2.hasScope('read')")
//                .antMatchers(HttpMethod.DELETE, "/oauth/revoke_token/**")
//                .access("#oauth2.clientHasRole('ROLE_CLIENT') and (hasRole('ROLE_USER') or #oauth2.isClient()) and #oauth2.hasScope('write')")
//                .regexMatchers(HttpMethod.DELETE, "/oauth/users/([^/].*?)/tokens/.*")
//                .access("#oauth2.clientHasRole('ROLE_CLIENT') and (hasRole('ROLE_USER') or #oauth2.isClient()) and #oauth2.hasScope('write')")
//                .regexMatchers(HttpMethod.GET, "/oauth/clients/([^/].*?)/users/.*")
//                .access("#oauth2.clientHasRole('ROLE_CLIENT') and (hasRole('ROLE_USER') or #oauth2.isClient()) and #oauth2.hasScope('read')")
//                .regexMatchers(HttpMethod.GET, "/oauth/clients/.*")
//                .access("#oauth2.clientHasRole('ROLE_CLIENT') and #oauth2.isClient() and #oauth2.hasScope('read')");
//
        // @formatter:off
        http
                // Since we want the protected resources to be accessible in the UI as well we need
                // session creation to be allowed (it's disabled by default in 2.0.6)
                //.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                //.and()
                .requestMatchers().antMatchers("/user", "/me", "/account","/testRole","/testFunction","/testRoleAndFunction")
                .and()
                .addFilter(corsFilter)
                .cors().disable()
                .authorizeRequests()
                //      .antMatchers("/user", "/me", "/oauth/account")
                //      .access("#oauth.hasScope('read')");
                .antMatchers("/user")
                .access("#oauth2.hasScope('read')")
                .antMatchers("/me")
                .access("#oauth2.hasScope('read')")
                .antMatchers("/account")
//                .access("#oauth2.hasScope('trust') and hasRole('USER2') and #func.hasFunction('ddddddddd') and hasPermission('BBBBB')");
                .access("#oauth2.hasScope('trust') and hasRole('aaa')")
		        .antMatchers("/testRole")
		        .access("#oauth2.hasScope('trust') and hasRole('aaa')")
		        .antMatchers("/testFunction")
		        .access("#oauth2.hasScope('trust') and #func.hasFunction('bbbb')")
		        .antMatchers("/testRoleAndFunction")
		        .access("#oauth2.hasScope('trust') and hasRole('aaa') and #func.hasFunction('bbbb')");
        		
        		
        // @formatter:on


        // org.springframework.security.oauth2.provider.expression.OAuth2SecurityExpressionMethods

        OAuth2SecurityExpressionMethods ddd;

        SecurityExpressionRoot sd;
    }

}
