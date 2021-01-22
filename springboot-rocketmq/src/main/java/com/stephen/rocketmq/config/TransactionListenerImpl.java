package com.stephen.rocketmq.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ssc on 2020-12-28 16:58 .
 * Description: 事务监听器的实现
 * 让业务类继承此类，并实现doBusiness方法，实现不同的业务
 */
@Slf4j
@Component
@RocketMQTransactionListener(rocketMQTemplateBeanName="rocketMQTemplate")
public class TransactionListenerImpl implements RocketMQLocalTransactionListener {

    private static ConcurrentHashMap<String,RocketMQLocalTransactionState> STATE_MAP = new ConcurrentHashMap<>();

    // 执行本地事务，若返回UNKNOW状态，则会执行下边的回查方法
    // 本地的操作需要放在这里,放在其它地方事务会失效
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object obj) {
        RocketMQLocalTransactionState state;
        // transId需要在发送消息时手动设置,否则为Null
        String transId = (String) msg.getHeaders().get(RocketMQHeaders.TRANSACTION_ID);
        if (StringUtils.isBlank(transId)) { // 当未设置transId时,也可自已生成一个
            transId = String.valueOf(msg.hashCode());
        }
        try {
            log.info("准备操作的本地数据{}" ,obj);
            log.info("执行本地业务逻辑,transId:{}",transId);
            // 执行本地操作
            // int i = 1 / 0;
            log.info("执行本地业务逻辑,COMMIT,transId:{}",transId);
            state = RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            log.error("执行本地业务逻辑 ROLLBACK,transId:{},{},{}",transId,e.getMessage(),e);
            state =  RocketMQLocalTransactionState.ROLLBACK;
        }
        STATE_MAP.put(transId,state);
        log.info("STATE_MAP,transId:{},state:{}",transId,state); // 出异常,也会执行打印这行日志
        return state;
    }

    // 回查UNKNOW状态的消息
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        String transId = (String) msg.getHeaders().get(RocketMQHeaders.TRANSACTION_ID);
        if (StringUtils.isBlank(transId)) {
            transId = String.valueOf(msg.hashCode());
        }
        log.info("回查消息 -> transId = {} ,state = {}",transId,STATE_MAP.get(transId));
        return STATE_MAP.get(transId);
    }
}
