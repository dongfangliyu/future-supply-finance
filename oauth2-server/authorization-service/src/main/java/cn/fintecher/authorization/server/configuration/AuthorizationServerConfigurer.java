package cn.fintecher.authorization.server.configuration;

import cn.fintecher.authorization.server.approval.OAuth2UserApprovalHandler;
import cn.fintecher.authorization.server.security.CustomizeClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenEndpointFilter;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfigurer extends AuthorizationServerConfigurerAdapter {

    private static final String REDIS_TOKEN_STORE_PREFIX = "authorization-server:";

    @Autowired
    public UserDetailsService userDetailsService;

    @Autowired
    public ClientDetailsService clientDetailsService;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private UserApprovalHandler userApprovalHandler;

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    /**
     * 配置AccessToken的存储方式：此处使用Redis存储
     * Token的可选存储方式
     * 1、InMemoryTokenStore
     * 2、JdbcTokenStore
     * 3、JwtTokenStore
     * 4、RedisTokenStore
     * 5、JwkTokenStore
     */
    @Bean
    public TokenStore tokenStore(RedisConnectionFactory redisConnectionFactory) {
        RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
        tokenStore.setPrefix(REDIS_TOKEN_STORE_PREFIX);
        return tokenStore;
    }

    @Bean
    public ClientDetailsService clientDetailsService() {

        return new CustomizeClientDetailsService();
    }

    @Bean
    public ApprovalStore approvalStore() throws Exception {
        TokenApprovalStore store = new TokenApprovalStore();
        store.setTokenStore(tokenStore);
        return store;
    }

    @Bean
    @Lazy
    @Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
    public OAuth2UserApprovalHandler userApprovalHandler() throws Exception {
        OAuth2UserApprovalHandler handler = new OAuth2UserApprovalHandler();
        handler.setApprovalStore(approvalStore());
        handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
        handler.setClientDetailsService(clientDetailsService);
        handler.setUseApprovalStore(true);
        return handler;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore)
                .userDetailsService(userDetailsService)
                .userApprovalHandler(userApprovalHandler)
                .authenticationManager(authenticationManager)
                .setClientDetailsService(clientDetailsService);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer securityConfigurer) throws Exception {
        securityConfigurer
                .allowFormAuthenticationForClients()
                .checkTokenAccess("isAuthenticated()")
                .addTokenEndpointAuthenticationFilter(checkTokenEndpointFilter());
    }

    @Bean
    public ClientCredentialsTokenEndpointFilter checkTokenEndpointFilter() {
        ClientCredentialsTokenEndpointFilter filter = new ClientCredentialsTokenEndpointFilter("/oauth/check_token");
        filter.setAuthenticationManager(authenticationManager);
        filter.setAllowOnlyPost(true);
        return filter;
    }

//    @Override
//    @Bean
//    public AuthorizationEndpoint authorizationEndpoint() throws Exception {
//
//        return new NotUseSessionAuthorizationEndpoint();
//    }

    //TODO 不起作用
//    private static final String REDIS_CACHE_NAME = "redis_cache_name";//不为null即可
//    private static final String REDIS_PREFIX = "redis_cache_prefix";//不为null即可
//    private static final Long EXPIRE = 60 * 60L;//缓存有效时间
//    /**
//     * 配置用以存储用户认证信息的缓存
//     */
//    @Bean
//    RedisCache redisCache(RedisTemplate redisTemplate) {
//        RedisCache redisCache = new RedisCache(REDIS_CACHE_NAME, REDIS_PREFIX.getBytes()
//                , redisTemplate, EXPIRE);
//        return redisCache;
//    }
//
//    /**
//     * 创建UserDetails存储服务的Bean：使用Redis作为缓存介质
//     * UserDetails user = this.userCache.getUserFromCache(username)
//     */
//    @Bean
//    public UserCache userCache(RedisCache redisCache) throws Exception {
//        UserCache userCache = new SpringCacheBasedUserCache(redisCache);
//        return userCache;
//    }

    //    @Autowired
//    public UserDetailsService userDetailsService;
//    @Bean
//    public TokenStore tokenStore() {
//        //RedisTokenStore
//        return new InMemoryTokenStore();
//    }
    //    @Value("${tonr.redirect:http://localhost:8081/user-manager/redirect}")
//    private String tonrRedirectUri;
//
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        // @formatter:off
//        clients.inMemory().withClient("tonr")
//                .resourceIds(OAuthConfig.RESOURCE_ID)
//                .authorizedGrantTypes("authorization_code", "implicit")
//                .authorities("ROLE_CLIENT")
//                .scopes("read", "write")
//                .secret("secret")
//                .and()
//                .withClient("authorization-user-manager")
//                .resourceIds("1234567890")
//                .authorizedGrantTypes("authorization_code", "implicit")
//                .authorities("ROLE_CLIENT")
//                .scopes("read", "write")
//                .secret("secret")
//                .redirectUris(tonrRedirectUri)
//                .and()
//                .withClient("my-client-with-registered-redirect")
//                .resourceIds(OAuthConfig.RESOURCE_ID)
//                .authorizedGrantTypes("authorization_code", "client_credentials")
//                .authorities("ROLE_CLIENT")
//                .scopes("read", "trust")
//                .secret("secret")
//                .accessTokenValiditySeconds(360)
//                //.redirectUris("http://anywhere?key=value")
//                .and()
//                .withClient("my-trusted-client")
//                .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
//                .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
//                .scopes("read", "write", "trust")
//                .accessTokenValiditySeconds(60)
//                .and()
//                .withClient("my-trusted-client-with-secret")
//                .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
//                .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
//                .scopes("read", "write", "trust")
//                .secret("somesecret")
//                .and()
//                .withClient("my-less-trusted-client")
//                .authorizedGrantTypes("authorization_code", "implicit")
//                .authorities("ROLE_CLIENT")
//                .scopes("read", "write", "trust")
//                .and()
//                .withClient("my-less-trusted-autoapprove-client")
//                .authorizedGrantTypes("implicit")
//                .authorities("ROLE_CLIENT")
//                .scopes("read", "write", "trust")
//                .autoApprove(true);
//        // @formatter:on
//    }

}
