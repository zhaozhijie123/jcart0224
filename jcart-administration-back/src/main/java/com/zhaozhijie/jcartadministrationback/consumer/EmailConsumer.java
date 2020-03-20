package com.zhaozhijie.jcartadministrationback.consumer;

import com.zhaozhijie.jcartadministrationback.util.MailBean;
import com.zhaozhijie.jcartadministrationback.util.MailService;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@RocketMQMessageListener(topic = "SendPwdResetByEmail", consumerGroup = "jcart-support-group01")
public class EmailConsumer implements RocketMQListener<MailBean> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MailService mailService;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    public void onMessage(MailBean mailBean) {
        logger.info("{}",mailBean);
        try {
            mailService.sendSimpleMail(mailBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
