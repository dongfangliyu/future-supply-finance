package cn.fintecher.authorization.server.configuration;


import cn.fintecher.authorization.server.security.CustomizeUserDetailsService;
import cn.fintecher.common.utils.confcommon.filter.EnableCorsFilterConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableCorsFilterConfigurer
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(-1)
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    private CorsFilter corsFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomizeUserDetailsService();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder()); //添加我们自定的user detail service认证；
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //  web.ignoring().antMatchers("/images/**",  "/oauth/vv1","/oauth/uncache_approvals", "/oauth/cache_approvals");
        web.ignoring().antMatchers("/oauth/v1");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .authorizeRequests().anyRequest().fullyAuthenticated()
                .and()
                .requestMatchers().antMatchers(HttpMethod.OPTIONS, "/oauth/**")
                .and()
                .addFilter(corsFilter)
                .csrf().disable();
        // @formatter:on
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        // @formatter:off
//        http
//                .authorizeRequests()
//                .antMatchers("/login.jsp").permitAll()
//                .anyRequest().hasRole("USER")
//                .and()
//                .exceptionHandling()
//                .accessDeniedPage("/login.jsp?authorization_error=true")
//                .and()
//                // TODO: put CSRF protection back into this endpoint
//                .csrf()
//                .requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize"))
//                .disable()
//                .logout()
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/login.jsp")
//                .and()
//                .formLogin()
//                .loginProcessingUrl("/login")
//                .failureUrl("/login.jsp?authentication_error=true")
//                .loginPage("/login.jsp");
//        // @formatter:on
//    }

//    @Override
//    public void configure(WebSecurity web) throws Exception {
////        web.ignoring().antMatchers("/oauth/token"
////                ,"/oauth/authorize"
////                ,"/oauth/uncache_approvals"
////                , "/oauth/cache_approvals");
//
//        web.ignoring().antMatchers("/oauth/token"
//                ,"/oauth/authorize");
//    }

    //    @Bean
//    RestAuthenticationEntryPoint restAuthenticationEntryPoint(){
//
//        return new RestAuthenticationEntryPoint();
//    }
//    @Bean
//    public AuditorAware<String> createAuditorProvider() {
//        return new SecurityAuditor();
//    }
//
//    @Bean
//    public AuditingEntityListener createAuditingListener() {
//        return new AuditingEntityListener();
//    }
//
//    public static class SecurityAuditor implements AuditorAware<String> {
//        @Override
//        public String getCurrentAuditor() {
//            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//            if(auth == null)
//                return CommonUtils.SysCreateUser;
//            String username = auth.getName();
//            return username;
//        }
//    }
//    @Autowired
//    public UserDetailsService userDetailsService;

    //    @Autowired
//    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("admin1").password("admin1").roles("USER").and().withUser("admin2")
//                .password("admin2").roles("USER");
//    }


}
