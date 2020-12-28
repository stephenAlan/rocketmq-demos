package com.stephen.rocketmq.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * Created by ssc on 2020-12-27 11:55 .
 * Description: 消费者
 * 事务消息也是用这个,事务是在生产者那边控制
 */
@Component
@Slf4j
@RocketMQMessageListener(topic = "TopicTest",selectorExpression = "*",consumerGroup = "test_consumer_group")
public class Consumer implements RocketMQListener<MessageExt> {
    @Override
    public void onMessage(MessageExt message) {
        log.info("接收到消息: {}",message);
        log.info("msg:{}",new String(message.getBody()));
    }
}
