package cn.fintecher.lock.lockservicer.service;

import cn.fintecher.common.utils.basecommon.message.Message;
import cn.fintecher.common.utils.basecommon.message.MessageType;
import cn.fintecher.lock.lockservicer.dao.RedisLockRepository;
import cn.fintecher.lock.lockservicer.dto.LockResultDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


/**
 * Created by lijinlong on 2017/05/26.
 */
@Service
public class RedisLockService {

    private final static String REDIS_LOCK = "REDIS_LOCK";

    private static final Logger logger = LoggerFactory.getLogger(RedisLockService.class);

    private final static int RETRY_COUNT = 4;

    private final static long SLEEP_TIME_MILLIS = 10;

    private final static String RESULT_TRUE = "true";

    private final static String RESULT_FALSE = "false";

    private final static String RESULT_FAILED = "failed";

    @Autowired
    RedisLockRepository redisLockRepository;

    public ResponseEntity<Message> trylock(String key, long timeout) {

        int retryCount = 0;

        LockResultDto lr = new LockResultDto();
        lr.setKey(key);
        lr.setTimeout(timeout);
        lr.setResult(RESULT_FAILED);
        lr.setMessage("");

        do {
            lr = doTryLock(key, timeout);

            if (!RESULT_FAILED.equals(lr.getResult())) {
                break;
            }

            retryCount += 1;

            sleep(SLEEP_TIME_MILLIS);

        } while (retryCount < RETRY_COUNT);

        if (RESULT_FAILED.equals(lr.getResult())) {
            return new ResponseEntity<Message>(new Message(MessageType.MSG_SUCCESS, REDIS_LOCK, lr), HttpStatus.OK);
        }

        return new ResponseEntity<Message>(new Message(MessageType.MSG_SUCCESS, REDIS_LOCK, lr), HttpStatus.OK);
    }

    private LockResultDto doTryLock(String key, long timeout) {
        LockResultDto lr = new LockResultDto();
        lr.setKey(key);
        lr.setTimeout(timeout);
        lr.setResult(RESULT_FAILED);
        lr.setMessage("");
        try {
            if (redisLockRepository.trylock(key, timeout)) {
                lr.setResult(RESULT_TRUE);
            } else {
                lr.setResult(RESULT_FALSE);
            }
        } catch (Exception ex) {
            //ignore
            lr.setMessage(ex.getMessage());
            logger.warn(String.format("doTryLock:%s:%s", key, lr.getMessage()));
        }
        return lr;
    }


    public ResponseEntity<Message> unlock(String key, long timeout) {

        int retryCount = 0;

        LockResultDto lr = new LockResultDto();
        lr.setKey(key);
        lr.setTimeout(timeout);
        lr.setResult(RESULT_FAILED);
        lr.setMessage("");
        do {
            lr = doUnlock(key, timeout);

            if (!RESULT_FAILED.equals(lr.getResult())) {
                break;
            }

            retryCount += 1;

            sleep(SLEEP_TIME_MILLIS);

        } while (retryCount < RETRY_COUNT);

        if (RESULT_FAILED.equals(lr.getResult())) {
            return new ResponseEntity<Message>(new Message(MessageType.MSG_SUCCESS, REDIS_LOCK, lr), HttpStatus.OK);
        }

        return new ResponseEntity<Message>(new Message(MessageType.MSG_SUCCESS, REDIS_LOCK, lr), HttpStatus.OK);
    }

    private LockResultDto doUnlock(String key, long timeout) {
        LockResultDto lr = new LockResultDto();
        lr.setKey(key);
        lr.setTimeout(timeout);
        lr.setResult(RESULT_FAILED);
        lr.setMessage("");
        try {
            redisLockRepository.unlock(key, timeout);

            lr.setResult(RESULT_TRUE);
        } catch (Exception ex) {
            //ignore
            lr.setMessage(ex.getMessage());
            logger.warn(String.format("doUnlock:%s:%s", key, lr.getMessage()));
        }
        return lr;
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
