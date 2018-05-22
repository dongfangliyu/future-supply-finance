package cn.fintecher.authorization.agent.controller;

import cn.fintecher.authorization.agent.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping({"/user", "/me"})
    @ResponseBody
    public Map<String, String> user(Principal principal) {
        return userInfoService.user(principal);
    }

  //  @PreAuthorize("hasPermission(#foo, 'write')")
    @ResponseBody
    @RequestMapping(value = "/account", method = RequestMethod.GET)
    public String account(Principal principal)
            throws Exception {
    	
        return userInfoService.account(principal);
    }
    
    @ResponseBody
    @RequestMapping(value = "/testRole", method = RequestMethod.GET)
    public String testRole(Principal principal)
            throws Exception {
    	
        return "success";
    }
    
    
    @ResponseBody
    @RequestMapping(value = "/testFunction", method = RequestMethod.GET)
    public String testFunction(Principal principal)
            throws Exception {
    	
        return "success";
    }
    
    
    @ResponseBody
    @RequestMapping(value = "/testRoleAndFunction", method = RequestMethod.GET)
    public String testRoleAndFunction(Principal principal)
            throws Exception {
    	
        return "success";
    }
    
    
}
