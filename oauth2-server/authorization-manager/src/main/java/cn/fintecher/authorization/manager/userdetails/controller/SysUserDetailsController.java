package cn.fintecher.authorization.manager.userdetails.controller;


import cn.fintecher.authorization.manager.userdetails.service.SysUserDetailsService;
import cn.fintecher.common.utils.basecommon.message.Message;
import cn.fintecher.common.utils.basecommon.message.MessageType;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public Message createUser(@RequestBody JSONObject jsonObject) {
        try {
            return sysUserDetailsService.createUser(jsonObject);
        } catch (Exception ex) {
            return new Message(MessageType.MSG_ERROR,"oauth2",ex.getMessage());
        }
    }

    @RequestMapping(value = "updateUser", method = RequestMethod.POST)
    @ResponseBody
    public Message updateUser(@RequestBody JSONObject jsonObject) {
        try {
            return sysUserDetailsService.updateUser(jsonObject);
        } catch (Exception ex) {
            return new Message(MessageType.MSG_ERROR, "oauth2",ex.getMessage());
        }
    }

    @RequestMapping(value = "getByUserName", method = RequestMethod.GET)
    @ResponseBody
    public Message getByUserName(@RequestParam("username") String username) {
        try {
            return sysUserDetailsService.getByUserName(username);
        } catch (Exception ex) {
            return new Message(MessageType.MSG_ERROR, "oauth2",ex.getMessage());
        }
    }
}
