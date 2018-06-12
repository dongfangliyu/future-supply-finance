package cn.fintecher.authorization.server.service;

import cn.fintecher.common.utils.basecommon.message.Message;
import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "authorization-manager", url = "${authorization.manager.url}")
public interface SysUserDetailsService {

    @RequestMapping(value = "sys-user-details/searchUserDetail", method = RequestMethod.GET)
    @ResponseBody
    ResponseEntity<Message> SearchUserDetail(@RequestParam("username") String username);

    @RequestMapping(value = "sys-user-details/createUser", method = RequestMethod.POST)
    @ResponseBody
    Message createUser(@RequestBody JSONObject jsonObject);

    @RequestMapping(value = "sys-user-details/updateUser", method = RequestMethod.POST)
    @ResponseBody
    Message updateUser(@RequestBody JSONObject jsonObject);

    @RequestMapping(value = "sys-user-details/getByUserName", method = RequestMethod.GET)
    @ResponseBody
    Message getByUserName(@RequestParam("username") String username);
}
