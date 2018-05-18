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
 * Created by lijinlong on 2017/05/26.
 */
@Repository
public class RedisLockRepository {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Resource(name = "stringRedisTemplate")
    ValueOperations<String, String> valOpsStr;

    public boolean trylock(String key, long timeout) {

        String value = valOpsStr.get(key);

        if ("true".equals(value)) {
            return false;
        }

        SessionCallback<String> sessionCallback = new SessionCallback<String>() {
            @Override
            public String execute(RedisOperations operations)
                    throws DataAccessException {
                operations.watch(key);

                operations.multi();

                operations.opsForValue().set(key, "true", timeout, TimeUnit.SECONDS);

                List<String> values = operations.exec();

                if (values != null && values.size() == 1) {
                    return values.get(0);
                }

                return "";
            }
        };

        stringRedisTemplate.execute(sessionCallback);

        return true;
    }

    public void unlock(String key, long timeout) {

        String value = valOpsStr.get(key);

        if (!"true".equals(value)) {
            return;
        }

        SessionCallback<String> sessionCallback = new SessionCallback<String>() {
            @Override
            public String execute(RedisOperations operations)
                    throws DataAccessException {
                operations.watch(key);

                operations.multi();

                operations.opsForValue().set(key, "false", timeout, TimeUnit.SECONDS);

                List<String> values = operations.exec();

                if (values != null && values.size() == 1) {
                    return values.get(0);
                }

                return "";
            }
        };

        stringRedisTemplate.execute(sessionCallback);
    }
}
