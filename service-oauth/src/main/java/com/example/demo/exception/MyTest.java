package com.example.demo.exception;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.store.redis.JdkSerializationStrategy;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStoreSerializationStrategy;

/**
 * Created by 超男 on 2019/3/6.
 */
public class MyTest {


    private final RedisConnectionFactory connectionFactory;

    public MyTest(RedisConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    private RedisConnection getConnection() {
        return this.connectionFactory.getConnection();
    }



    public void storeAccessToken(String value) {
        byte[] serializedAccessToken = value.getBytes();
        byte[] accessKey = ("shiyakun:" + value).getBytes();


        RedisConnection conn = getConnection();
        try {
            conn.openPipeline();
            conn.stringCommands().set(accessKey, serializedAccessToken);
            conn.closePipeline();
        } finally {
            conn.close();
        }
    }
}
