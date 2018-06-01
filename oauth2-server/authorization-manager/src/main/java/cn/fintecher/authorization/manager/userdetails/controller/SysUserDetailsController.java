package cn.fintecher.authorization.manager.userdetails.controller;


import cn.fintecher.authorization.manager.userdetails.service.SysUserDetailsService;
import cn.fintecher.common.utils.basecommon.message.Message;
import cn.fintecher.common.utils.basecommon.message.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("sys-user-details")
public class SysUserDetailsController {
    @Autowired
    private SysUserDetailsService sysUserDetailsService;

    @RequestMapping(value = "searchUserDetail", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Message> SearchUserDetail(@RequestParam("username") String username) {
        try {
            return sysUserDetailsService.SearchUserDetail(username);
        } catch (Exception ex) {
            //  logger.error("返回所有客户error", ex);
            return new ResponseEntity<Message>(new Message(MessageType.MSG_ERROR, "oauth2",ex.getMessage()), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "createUser", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Message> createUser(Map<String,Object> map) {
        try {
            return sysUserDetailsService.createUser(map);
        } catch (Exception ex) {
            return new ResponseEntity<Message>(new Message(MessageType.MSG_ERROR, "oauth2",ex.getMessage()), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "updateUser", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Message> updateUser(Map<String,Object> map) {
        try {
            return sysUserDetailsService.updateUser(map);
        } catch (Exception ex) {
            return new ResponseEntity<Message>(new Message(MessageType.MSG_ERROR, "oauth2",ex.getMessage()), HttpStatus.OK);
        }
    }
}
