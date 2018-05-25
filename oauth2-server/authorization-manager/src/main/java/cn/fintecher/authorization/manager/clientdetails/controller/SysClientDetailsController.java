package cn.fintecher.authorization.manager.clientdetails.controller;


import cn.fintecher.authorization.manager.clientdetails.service.SysClientDetailsService;
import cn.fintecher.common.utils.basecommon.message.Message;
import cn.fintecher.common.utils.basecommon.message.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("sys-client-details")
public class SysClientDetailsController {

    @Autowired
    private SysClientDetailsService sysClientDetailsService;

    @RequestMapping(value = "searchClientDetail", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Message> SearchClientDetail(@RequestParam("clientId") String clientId) {
        try {
            return sysClientDetailsService.SearchClientDetail(clientId);
        } catch (Exception ex) {
            //  logger.error("返回所有客户error", ex);
            return new ResponseEntity<Message>(new Message(MessageType.MSG_ERROR,"oauth2", ex.getMessage()), HttpStatus.OK);
        }
    }
}
