package cn.fintecher.lock.lockservicer.service;

import cn.fintecher.common.utils.basecommon.message.Message;
import cn.fintecher.common.utils.basecommon.message.MessageType;
import cn.fintecher.lock.lockservicer.dao.RedisCounterRepository;
import cn.fintecher.lock.lockservicer.dto.CounterResultDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Created by lijinlong on 2017/05/27.
 */
@Service
public class RedisCounterService {

    final static String REDIS_COUNTER = "REDIS_COUNTER";
    final static int RETRY_COUNT = 4;
    final static long SLEEP_TIME_MILLIS = 10;
    private static final Logger logger = LoggerFactory.getLogger(RedisCounterService.class);
    @Autowired
    RedisCounterRepository redisCounterRepository;

    public ResponseEntity<Message> incr(String key, long timeout) {

        CounterResultDto cr = new CounterResultDto();
        cr.setKey(key);
        cr.setTimeout(timeout);
        cr.setValue(0L);
        cr.setSuccess(false);
        cr.setMessage("");

        int retryCount = 0;

        do {
            cr = doIncrement(key, timeout);

            if (cr.getSuccess() == true) {
                break;
            }

            retryCount += 1;

            sleep(SLEEP_TIME_MILLIS);

        } while (retryCount < RETRY_COUNT);

        if (cr.getSuccess() == false) {
            return new ResponseEntity<Message>(new Message(MessageType.MSG_SUCCESS, REDIS_COUNTER, cr), HttpStatus.OK);
        }

        return new ResponseEntity<Message>(new Message(MessageType.MSG_SUCCESS, REDIS_COUNTER, cr), HttpStatus.OK);
    }

    private CounterResultDto doIncrement(String key, long timeout) {
        CounterResultDto cr = new CounterResultDto();
        cr.setKey(key);
        cr.setTimeout(timeout);
        cr.setValue(0L);
        cr.setSuccess(false);
        cr.setMessage("");
        try {
            Long res = redisCounterRepository.incr(key, timeout);
            cr.setValue(res);
            cr.setSuccess(true);
        } catch (Exception ex) {
            //ignore
            cr.setSuccess(false);
            cr.setMessage(ex.getMessage());
            logger.warn(String.format("doIncrement:%s:%s", key, cr.getMessage()));
        }
        return cr;
    }


    public ResponseEntity<Message> decr(String key, long timeout) {

        CounterResultDto cr = new CounterResultDto();
        cr.setKey(key);
        cr.setTimeout(timeout);
        cr.setValue(0L);
        cr.setSuccess(false);
        cr.setMessage("");

        int retryCount = 0;

        do {
            cr = doDecrement(key, timeout);

            if (cr.getSuccess() == true) {
                break;
            }

            retryCount += 1;

            sleep(SLEEP_TIME_MILLIS);

        } while (retryCount < RETRY_COUNT);

        if (cr.getSuccess() == false) {
            return new ResponseEntity<Message>(new Message(MessageType.MSG_SUCCESS, REDIS_COUNTER, cr), HttpStatus.OK);
        }

        return new ResponseEntity<Message>(new Message(MessageType.MSG_SUCCESS, REDIS_COUNTER, cr), HttpStatus.OK);
    }

    private CounterResultDto doDecrement(String key, long timeout) {
        CounterResultDto cr = new CounterResultDto();
        cr.setKey(key);
        cr.setTimeout(timeout);
        cr.setValue(0L);
        cr.setSuccess(false);
        cr.setMessage("");
        try {
            Long res = redisCounterRepository.decr(key, timeout);
            cr.setValue(res);
            cr.setSuccess(true);
        } catch (Exception ex) {
            //ignore
            cr.setSuccess(false);
            cr.setMessage(ex.getMessage());
            logger.warn(String.format("doDecrement:%s:%s", key, cr.getMessage()));
        }
        return cr;
    }

    public ResponseEntity<Message> get(String key) {

        CounterResultDto cr = new CounterResultDto();
        cr.setKey(key);
        cr.setTimeout(0L);
        cr.setValue(0L);
        cr.setSuccess(false);
        cr.setMessage("");

        int retryCount = 0;

        do {
            cr = doGet(key);

            if (cr.getSuccess() == true) {
                break;
            }

            retryCount += 1;

            sleep(SLEEP_TIME_MILLIS);

        } while (retryCount < RETRY_COUNT);

        if (cr.getSuccess() == false) {
            return new ResponseEntity<Message>(new Message(MessageType.MSG_SUCCESS, REDIS_COUNTER, cr), HttpStatus.OK);
        }

        return new ResponseEntity<Message>(new Message(MessageType.MSG_SUCCESS, REDIS_COUNTER, cr), HttpStatus.OK);
    }

    public ResponseEntity<Message> reset(String key, long timeout) {

        CounterResultDto cr = new CounterResultDto();
        cr.setKey(key);
        cr.setTimeout(0L);
        cr.setValue(0L);
        cr.setSuccess(false);
        cr.setMessage("");

        int retryCount = 0;

        do {
            cr = doReset(key, timeout);

            if (cr.getSuccess() == true) {
                break;
            }

            retryCount += 1;

            sleep(SLEEP_TIME_MILLIS);

        } while (retryCount < RETRY_COUNT);

        if (cr.getSuccess() == false) {
            return new ResponseEntity<Message>(new Message(MessageType.MSG_SUCCESS, REDIS_COUNTER, cr), HttpStatus.OK);
        }

        return new ResponseEntity<Message>(new Message(MessageType.MSG_SUCCESS, REDIS_COUNTER, cr), HttpStatus.OK);
    }

    private CounterResultDto doGet(String key) {

        CounterResultDto cr = new CounterResultDto();
        cr.setKey(key);
        cr.setTimeout(0L);
        cr.setValue(0L);
        cr.setSuccess(false);
        cr.setMessage("");
        try {
            Long res = redisCounterRepository.get(key);
            cr.setValue(res);
            cr.setSuccess(true);
        } catch (Exception ex) {
            //ignore
            cr.setSuccess(false);
            cr.setMessage(ex.getMessage());
            logger.warn(String.format("doGet:%s:%s", key, cr.getMessage()));
        }
        return cr;
    }


    private CounterResultDto doReset(String key, long timeout) {

        CounterResultDto cr = new CounterResultDto();
        cr.setKey(key);
        cr.setTimeout(0L);
        cr.setValue(0L);
        cr.setSuccess(false);
        cr.setMessage("");
        try {
            Long res = redisCounterRepository.reset(key, timeout);
            cr.setValue(res);
            cr.setSuccess(true);
        } catch (Exception ex) {
            //ignore
            cr.setSuccess(false);
            cr.setMessage(ex.getMessage());
            logger.warn(String.format("doGet:%s:%s", key, cr.getMessage()));
        }
        return cr;
    }

    private void sleep(long millis) {

        try {
            Thread.sleep(millis);
        } catch (Exception e) {
            //ignore
            logger.warn(e.getMessage());
        }
    }
}
