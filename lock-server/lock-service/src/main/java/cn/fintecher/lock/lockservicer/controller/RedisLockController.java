package cn.fintecher.lock.lockservicer.controller;

import cn.fintecher.common.utils.basecommon.message.Message;
import cn.fintecher.common.utils.basecommon.message.MessageType;
import cn.fintecher.lock.lockservicer.service.RedisLockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lijinlong on 2017/05/26.
 */
@RestController
@RequestMapping("api/lockservice/redislock")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RedisLockController {
    final static long DEFALUT_TIMEOUT = 3600;

    final static String REDIS_LOCK = "REDIS_LOCK";

    private static final Logger logger = LoggerFactory.getLogger(RedisLockController.class);
    @Autowired
    private RedisLockService redisLockService;


    @RequestMapping(value = "trylock", method = RequestMethod.GET)
    public ResponseEntity<Message> trylock(String key, long timeout) {

        String msg = "";
        try {
            if (key == null)
                return new ResponseEntity<Message>(new Message(MessageType.MSG_ERROR,
                        REDIS_LOCK, "input key param is null!"), HttpStatus.OK);

            if (timeout <= 0)
                timeout = DEFALUT_TIMEOUT;

            return redisLockService.trylock(key, timeout);

        } catch (Exception ex) {
            //ignore
            msg = ex.getMessage();
            logger.warn(String.format("trylock:%s:%s", key, msg));
        }

        return new ResponseEntity<Message>(new Message(MessageType.MSG_ERROR, REDIS_LOCK, msg), HttpStatus.OK);

    }

    @RequestMapping(value = "unlock", method = RequestMethod.GET)
    public ResponseEntity<Message> unlock(String key, long timeout) {

        String msg = "";
        try {
            if (key == null)
                return new ResponseEntity<Message>(new Message(MessageType.MSG_ERROR,
                        REDIS_LOCK, "input key param is null!"), HttpStatus.OK);

            if (timeout <= 0)
                timeout = DEFALUT_TIMEOUT;

            return redisLockService.unlock(key, timeout);

        } catch (Exception ex) {
            //ignore
            msg = ex.getMessage();
            logger.warn(String.format("unlock:%s:%s", key, msg));
        }

        return new ResponseEntity<Message>(new Message(MessageType.MSG_ERROR, REDIS_LOCK, msg), HttpStatus.OK);
    }


}
