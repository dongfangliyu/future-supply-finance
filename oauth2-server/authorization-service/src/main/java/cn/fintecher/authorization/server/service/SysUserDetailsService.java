package cn.fintecher.authorization.server.service;

import cn.fintecher.common.utils.basecommon.message.Message;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@FeignClient(name = "authorization-manager", url = "${authorization.manager.url}")
public interface SysUserDetailsService {

    @RequestMapping(value = "sys-user-details/searchUserDetail", method = RequestMethod.GET)
    @ResponseBody
    ResponseEntity<Message> SearchUserDetail(@RequestParam("username") String username);

    @RequestMapping(value = "sys-user-details/createUser", method = RequestMethod.POST)
    @ResponseBody
    ResponseEntity<Message> createUser(Map<String,Object> map);

    @RequestMapping(value = "sys-user-details/updateUser", method = RequestMethod.POST)
    @ResponseBody
    ResponseEntity<Message> updateUser(Map<String,Object> map);

    @RequestMapping(value = "sys-user-details/getByUserName", method = RequestMethod.GET)
    @ResponseBody
    ResponseEntity<Message> getByUserName(String userName);
}
