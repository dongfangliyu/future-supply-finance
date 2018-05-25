package cn.fintecher.authorization.agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@SpringBootApplication
@EnableOAuth2Client
@EnableFeignClients
public class AuthorizationUserAgentApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthorizationUserAgentApplication.class, args);
    }
}
