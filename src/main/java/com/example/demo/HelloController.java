package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisSentinelConnection;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.*;

/**
 * @author shaofu
 */
@RestController
public class HelloController {
    public static final Logger log = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private RedisUtils redisUtils;

    @RequestMapping(method = RequestMethod.GET, path = "/hello/{id}")
    public String hello(@PathVariable(value = "id") String id) {
        //查询缓存中是否存在
        boolean hasKey = redisUtils.exists(id);

        RedisSentinelConnection sentinelConnection = redisUtils.getRedisTemplate().getConnectionFactory().getSentinelConnection();

        String str = "";
        if (hasKey) {
            //获取缓存
            Object object = redisUtils.get(id);
            log.info("从缓存获取的数据" + object);
            str = object.toString();

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    Object object = redisUtils.get(id);
                    log.info("从缓存获取的数据" + object);
                    String str = object.toString();
                }
            };

            /**
            new Thread(runnable).start();
            new Thread(runnable).start();
            new Thread(runnable).start();
            new Thread(runnable).start();
            new Thread(runnable).start();
             **/

            ExecutorService myExecutor = new ThreadPoolExecutor(4, 5, 0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>(1000));
            for (int i = 0; i < 100; i++) {
                myExecutor.execute(runnable);
            }
        } else {
            str = System.currentTimeMillis() + "";
            redisUtils.set(id, str, 10L, TimeUnit.MINUTES);
            log.info("数据插入缓存" + str);
        }

        // return System.currentTimeMillis() + " Hello";
        return str;
    }

    public static void main(String[] args) {
        System.out.println(Runtime.getRuntime().availableProcessors());
    }
}