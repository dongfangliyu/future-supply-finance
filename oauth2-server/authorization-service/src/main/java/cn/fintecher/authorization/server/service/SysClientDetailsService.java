package cn.fintecher.authorization.server.service;

import cn.fintecher.common.utils.basecommon.message.Message;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(name = "authorization-manager", url = "${authorization.manager.url}")
public interface SysClientDetailsService {
    @RequestMapping(value = "sys-client-details/searchClientDetail", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Message> SearchClientDetail(@RequestParam("clientId") String clientId);

}
