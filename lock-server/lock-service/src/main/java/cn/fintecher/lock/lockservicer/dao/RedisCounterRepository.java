package cn.fintecher.lock.lockservicer.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by lijinlong on 2017/05/27.
 */
@Repository
public class RedisCounterRepository {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Resource(name = "stringRedisTemplate")
    ValueOperations<String, String> valOpsStr;

    public Long incr(String key, long timeout) {

        SessionCallback<Long> sessionCallback = new SessionCallback<Long>() {
            @Override
            public Long execute(RedisOperations operations)
                    throws DataAccessException {

                Object objValue = operations.opsForValue().get(key);

                Long nResult = 0L;

                if (objValue == null) {

                    operations.watch(key);

                    operations.multi();

                    operations.opsForValue().increment(key, 1L);

                    List<Long> values = operations.exec();

                    if (values != null && values.size() == 1) {
                        nResult = values.get(0);
                    }
                } else {
                    nResult = operations.opsForValue().increment(key, 1L);
                }

                return nResult;
            }
        };

        Long nValue = stringRedisTemplate.execute(sessionCallback);

        stringRedisTemplate.expire(key, timeout, TimeUnit.SECONDS);

        return nValue;
    }

    public Long decr(String key, long timeout) {

        SessionCallback<Long> sessionCallback = new SessionCallback<Long>() {
            @Override
            public Long execute(RedisOperations operations)
                    throws DataAccessException {

                Object objValue = operations.opsForValue().get(key);

                Long nResult = 0L;

                if (objValue == null) {

                    operations.watch(key);

                    operations.multi();

                    operations.opsForValue().increment(key, -1L);

                    List<Long> values = operations.exec();

                    if (values != null && values.size() == 1) {
                        nResult = values.get(0);
                    }
                } else {
                    nResult = operations.opsForValue().increment(key, -1L);
                }

                return nResult;
            }
        };

        Long nValue = stringRedisTemplate.execute(sessionCallback);

        stringRedisTemplate.expire(key, timeout, TimeUnit.SECONDS);

        return nValue;
    }

    public Long get(String key) {

        String strValue = valOpsStr.get(key);

        if (strValue == null || "".equals(strValue))
            return 0L;

        return Long.parseLong(strValue, 10);
    }

    public Long reset(String key, long timeout) {
        SessionCallback<Long> sessionCallback = new SessionCallback<Long>() {
            @Override
            public Long execute(RedisOperations operations)
                    throws DataAccessException {

                Object objValue = operations.opsForValue().get(key);

                Long nResult = 0L;

                if (objValue != null) {
                    Long num = get(key) * -1;
                    nResult = operations.opsForValue().increment(key,num);
                }
                return nResult;
            }
        };

        Long nValue = stringRedisTemplate.execute(sessionCallback);

        stringRedisTemplate.expire(key, timeout, TimeUnit.SECONDS);

        return nValue;
    }

}
