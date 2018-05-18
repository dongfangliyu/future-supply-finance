package cn.fintecher.authorization.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AuthorizationUserManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthorizationUserManagerApplication.class, args);
    }
}