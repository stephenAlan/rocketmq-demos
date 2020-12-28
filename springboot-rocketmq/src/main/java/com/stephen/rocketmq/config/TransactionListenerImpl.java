package com.stephen.rocketmq.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ssc on 2020-12-28 16:58 .
 * Description: 事务监听器的实现
 */
@Slf4j
@Component
@RocketMQTransactionListener(rocketMQTemplateBeanName="rocketMQTemplate")
public class TransactionListenerImpl implements RocketMQLocalTransactionListener {

    private static Map<String,RocketMQLocalTransactionState> STATE_MAP = new HashMap<>();

    // 执行本地事务
    // 本地的操作需要放在这里,放在其它地方事务会失效
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        String transId = (String) msg.getHeaders().get(RocketMQHeaders.TRANSACTION_ID);
        try {
            log.info("执行本地操作");
            // int i = 1 / 0;
            STATE_MAP.put(transId,RocketMQLocalTransactionState.COMMIT);
            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            log.info(e.getMessage(),e);
        }
        STATE_MAP.put(transId,RocketMQLocalTransactionState.ROLLBACK);
        return RocketMQLocalTransactionState.ROLLBACK;
    }

    // 回查UNKNOW状态的消息
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        String transId = (String) msg.getHeaders().get(RocketMQHeaders.TRANSACTION_ID);
        log.info("回查消息 -> transId = {} ,state = {}",transId,STATE_MAP.get(transId));
        return STATE_MAP.get(transId);
    }
}
