package cn.fintecher.authorization.agent.service;

import cn.fintecher.common.utils.basecommon.message.Message;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(name = "authorization-manager", url = "${authorization.manager.url}")
public interface SysUserDetailsService {

    @RequestMapping(value = "sys-user-details/searchUserDetail", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Message> SearchUserDetail(@RequestParam("username") String username);
//    @FeignClient(name="Simple-Gateway")
//    interface GatewayClient {
//        @Headers("X-Auth-Token: {token}")
//        @RequestMapping(method = RequestMethod.GET, value = "/gateway/test")
//        String getSessionId(@Param("token") String token);
//    }
//    @Bean
//    public RequestInterceptor requestInterceptor() {
//
//        return new RequestInterceptor() {
//
//            @Override
//            public void apply(RequestTemplate template) {
//
//                template.header("X-Auth-Token", "some_token");
//            }
//        };
//    }


//    @FeignClient(name="Simple-Gateway")
//    interface GatewayClient {
//        @RequestMapping(method = RequestMethod.GET, value = "/gateway/test")
//        String getSessionId(@RequestHeader("X-Auth-Token") String token);
//    }
}
