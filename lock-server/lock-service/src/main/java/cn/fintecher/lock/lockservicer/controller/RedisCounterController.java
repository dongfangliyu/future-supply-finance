package cn.fintecher.lock.lockservicer.controller;

import cn.fintecher.common.utils.basecommon.message.Message;
import cn.fintecher.common.utils.basecommon.message.MessageType;
import cn.fintecher.lock.lockservicer.service.RedisCounterService;
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
 * Created by lijinlong on 2017/05/27.
 */
@RestController
@RequestMapping("api/lockservice/rediscounter")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RedisCounterController {
    final static long DEFALUT_TIMEOUT = 3600;

    final static String REDIS_COUNTER = "REDIS_COUNTER";

    private static final Logger logger = LoggerFactory.getLogger(RedisLockController.class);

    @Autowired
    private RedisCounterService redisCounterService;

    @RequestMapping(value = "incr", method = RequestMethod.GET)
    public ResponseEntity<Message> incr(String key, long timeout) {
        String msg = "";
        try {
            if (key == null)
                return new ResponseEntity<Message>(new Message(MessageType.MSG_ERROR,
                        REDIS_COUNTER, "input key param is null!"), HttpStatus.OK);

            if (timeout <= 0)
                timeout = DEFALUT_TIMEOUT;

            return redisCounterService.incr(key, timeout);

        } catch (Exception ex) {
            //ignore
            msg = ex.getMessage();
            logger.warn(String.format("incr:%s:%s", key, msg));
        }

        return new ResponseEntity<Message>(new Message(MessageType.MSG_ERROR, REDIS_COUNTER, msg), HttpStatus.OK);
    }

    @RequestMapping(value = "decr", method = RequestMethod.GET)
    public ResponseEntity<Message> decr(String key, long timeout) {
        String msg = "";
        try {
            if (key == null)
                return new ResponseEntity<Message>(new Message(MessageType.MSG_ERROR,
                        REDIS_COUNTER, "input key param is null!"), HttpStatus.OK);

            if (timeout <= 0)
                timeout = DEFALUT_TIMEOUT;

            return redisCounterService.decr(key, timeout);

        } catch (Exception ex) {
            //ignore
            msg = ex.getMessage();
            logger.warn(String.format("decr:%s:%s", key, msg));
        }

        return new ResponseEntity<Message>(new Message(MessageType.MSG_ERROR, REDIS_COUNTER, msg), HttpStatus.OK);
    }

    @RequestMapping(value = "get", method = RequestMethod.GET)
    public ResponseEntity<Message> get(String key) {
        String msg = "";
        try {
            if (key == null)
                return new ResponseEntity<Message>(new Message(MessageType.MSG_ERROR,
                        REDIS_COUNTER, "input key param is null!"), HttpStatus.OK);

            return redisCounterService.get(key);

        } catch (Exception ex) {
            //ignore
            msg = ex.getMessage();
            logger.warn(String.format("get:%s:%s", key, msg));
        }

        return new ResponseEntity<Message>(new Message(MessageType.MSG_ERROR, REDIS_COUNTER, msg), HttpStatus.OK);
    }

    @RequestMapping(value = "reset", method = RequestMethod.GET)
    public ResponseEntity<Message> reset(String key, long timeout) {
        String msg = "";
        try {
            if (key == null)
                return new ResponseEntity<Message>(new Message(MessageType.MSG_ERROR,
                        REDIS_COUNTER, "input key param is null!"), HttpStatus.OK);

            return redisCounterService.reset(key, timeout);

        } catch (Exception ex) {
            //ignore
            msg = ex.getMessage();
            logger.warn(String.format("get:%s:%s", key, msg));
        }

        return new ResponseEntity<Message>(new Message(MessageType.MSG_ERROR, REDIS_COUNTER, msg), HttpStatus.OK);
    }
}
