/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: thinking-in-spring-boot
 * @Author : <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @date : 2020-08-31
 * @version : 1.0.0-RELEASE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.fufeng.stream.rabbitmq.listener;

import lombok.extern.slf4j.Slf4j;
import org.fufeng.common.stream.MessageStreamProcessor;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * @program: thinking-in-spring-boot
 * @description: 监听日志
 * @author: <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @create: 2020-08-31
 */
@Slf4j
@Component
public class LogMessageListener {

    /**
     * 通过 MessageStreamProcessor.MESSAGE_INPUT 接收消息
     * 然后通过 SendTo 将处理后的消息发送到 MyProcessor.LOG_FORMAT_OUTPUT
     * @param message
     * @return
     */
    @StreamListener(MessageStreamProcessor.MESSAGE_INPUT)
    @SendTo(MessageStreamProcessor.LOG_FORMAT_OUTPUT)
    public String processLogMessage(String message) {
        log.info("接收到原始消息：" + message);
        return "「" + message +"」";
    }

//    @StreamListener(MessageStreamProcessor.MESSAGE_INPUT)
//    @SendTo(MessageStreamProcessor.LOG_FORMAT_OUTPUT)
//    public String processObjectLogMessage(LogInfo logInfo) {
//        log.info("接收到原始 object 消息：" + logInfo.toString());
//        return "「" + logInfo.toString() +"」";
//    }

    /**
     * 接收来自 MessageStreamProcessor.LOG_FORMAT_INPUT 的消息
     * 也就是加工后的消息，也就是通过上面的 SendTo 发送来的
     * 因为 MessageStreamProcessor.LOG_FORMAT_OUTPUT 和 MessageStreamProcessor.LOG_FORMAT_INPUT 是指向同一 exchange
     * @param message
     */
    @StreamListener(MessageStreamProcessor.LOG_FORMAT_INPUT)
    public void processFormatLogMessage(String message) {
        log.info("接收到格式化后的消息：" + message);
    }
}
