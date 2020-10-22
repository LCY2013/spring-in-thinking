/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: thinking-in-spring
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
package org.fufeng.stream.rabbitmq.controller;

import lombok.extern.slf4j.Slf4j;
import org.fufeng.common.stream.LogInfo;
import org.fufeng.common.stream.MessageStreamProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.time.Instant;
import java.util.*;

/**
 * @program: thinking-in-spring
 * @description: 生产者控制器
 * @author: <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @create: 2020-08-31
 */
@Slf4j
@RestController
@EnableBinding({MessageStreamProcessor.class})
public class ProducerController {

    @Autowired
    MessageStreamProcessor messageStreamProcessor;

    @GetMapping("/send/{message}")
    void sendMessage(@PathVariable String message) {
        final Message<String> stringMessage =
                MessageBuilder.withPayload(message).build();
        this.messageStreamProcessor.logOutput().send(stringMessage);
    }

    @GetMapping("/send/obj/{message}")
    void sendObjectMessage(@PathVariable String message) {
        final LogInfo logInfo = new LogInfo();
        logInfo.setClientIp(localIp());
        logInfo.setClientVersion("mac");
        logInfo.setTime(Date.from(Instant.now()));
        logInfo.setUserId(message);
        final Message<LogInfo> logInfoMessage =
                MessageBuilder.withPayload(logInfo).build();
        this.messageStreamProcessor.logOutput().send(logInfoMessage);
    }

    /**
     * 获取本机IP地址
     *
     * @return IP地址
     */
    String localIp() {
        // TODO Auto-generated method stub
        InetAddress ia = null;
        try {
            ia = InetAddress.getLocalHost();

            // String localName = ia.getHostName();
            // System.out.println("本机名称是：" + localname);
            // System.out.println("本机的ip是 ：" + localip);
            return ia.getHostAddress();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "127.0.0.1";
    }

    /**
     * 获取本机所有IP v4
     *
     * @return 获取本机所有IP v4
     */
    List<String> localIps() {
        List<String> ipList = new ArrayList<>();
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            NetworkInterface networkInterface;
            Enumeration<InetAddress> inetAddresses;
            InetAddress inetAddress;
            String ip;
            while (networkInterfaces.hasMoreElements()) {
                networkInterface = networkInterfaces.nextElement();
                inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    inetAddress = inetAddresses.nextElement();
                    if (inetAddress != null && inetAddress instanceof Inet4Address) { // IPV4
                        ip = inetAddress.getHostAddress();
                        ipList.add(ip);
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return ipList;
    }
}
