package cn.fintecher.lock.lockservicer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Created by lijinlong on 2017/05/26.
 */
@EnableEurekaClient
@SpringBootApplication
public class LockServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LockServiceApplication.class, args);
    }

}
