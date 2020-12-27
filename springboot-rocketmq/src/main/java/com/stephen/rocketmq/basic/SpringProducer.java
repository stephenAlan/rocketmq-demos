package com.stephen.rocketmq.basic;

import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by ssc on 2020-12-26 21:22 .
 * Description:
 */
@Component
public class SpringProducer {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    public void send(String topic,String msg) {
        this.rocketMQTemplate.convertAndSend(topic,msg);
    }

    public void sendInTransaction(String topic,String msg) throws InterruptedException {
        // String[] tags = new String[] {"TagA","TagB","TagC","TagD","TagE"};
        // for (int i = 0; i < 10; i++) {
            Message<String> message = MessageBuilder.withPayload(msg)
                    // .setHeader(RocketMQHeaders.TRANSACTION_ID, "TransID" + i)
                    // .setHeader(RocketMQHeaders.TAGS, tags[i % tags.length])
                    // .setHeader("MyProp", "MyProp" + i)
                    .build();
        //     String destination = topic + ":" + tags[i % tags.length];
        //     TransactionSendResult sendResult = rocketMQTemplate.sendMessageInTransaction(destination, message, destination);
        //     System.out.printf("%s%n",sendResult);
        //     Thread.sleep(10);
        // }
        TransactionSendResult sendResult = rocketMQTemplate.sendMessageInTransaction("TopicTest:tran", message, null);
        System.out.println(sendResult);

    }


}
