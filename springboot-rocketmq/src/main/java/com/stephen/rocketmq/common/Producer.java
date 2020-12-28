package com.stephen.rocketmq.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
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
@Slf4j
public class Producer {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    SendCallback sendCallback = new SendCallback() {

        // 发送消息成功之后的操作
        @Override
        public void onSuccess(SendResult sendResult) {
            log.info("发送消息成功,msgId:{}",sendResult.getMsgId());
        }

        // 发送消息失败之后的操作
        @Override
        public void onException(Throwable e) {
            log.info("发送消息失败,{},{}",e.getMessage(),e);
        }
    };

    public void send(String topic,String tag,String msg) {
        this.rocketMQTemplate.asyncSend(
                String.format("%s:%s",topic,tag),
                msg,
                sendCallback);
    }

    public TransactionSendResult sendTransaction(String topic,String msg,String tag) {
        log.info("准备发送Transaction消息:{}",msg);
        Message message = MessageBuilder.withPayload(msg).build();
        TransactionSendResult sendResult = rocketMQTemplate.sendMessageInTransaction(topic, message, tag);
        log.info("Transaction消息发送状态：{}",sendResult.getLocalTransactionState());
        return sendResult;
    }

    // public void sendInTransaction(String topic,String msg) throws InterruptedException {
    //     // String[] tags = new String[] {"TagA","TagB","TagC","TagD","TagE"};
    //     // for (int i = 0; i < 10; i++) {
    //         Message<String> message = MessageBuilder.withPayload(msg)
    //                 // .setHeader(RocketMQHeaders.TRANSACTION_ID, "TransID" + i)
    //                 // .setHeader(RocketMQHeaders.TAGS, tags[i % tags.length])
    //                 // .setHeader("MyProp", "MyProp" + i)
    //                 .build();
    //     //     String destination = topic + ":" + tags[i % tags.length];
    //     //     TransactionSendResult sendResult = rocketMQTemplate.sendMessageInTransaction(destination, message, destination);
    //     //     System.out.printf("%s%n",sendResult);
    //     //     Thread.sleep(10);
    //     // }
    //     TransactionSendResult sendResult = rocketMQTemplate.sendMessageInTransaction("TopicTest:tran", message, null);
    //     System.out.println(sendResult);
    //
    // }


}
