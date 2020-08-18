/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: spring-in-thinking
 * @Author : <a href="https://github.com/lcy2013">MagicLuo</a>
 * @date : 2020-08-18
 * @version : 1.0.0-RELEASE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.fufeng.websocket;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: spring-in-thinking
 * @description: {@link }
 * @author: <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @create: 2020-08-18
 */
@ServerEndpoint("/ws/{userName}")
public class ChatRootEndpoint {

    // 用于保存上下人数
    private final static Map<String, Session> livingSessions = new ConcurrentHashMap<>();
    // 用户名称映射
    private final static Map<String, String> nameMapping = new ConcurrentHashMap<>();

    @OnOpen
    public void openSession(@PathParam("userName") String userName, Session session) {
        // 参数不合法
        if (Objects.isNull(userName) || "".equals(userName)) {
            try {
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        if (session.isOpen()) {
            // 获取会话ID
            final String sessionId = session.getId();
            if (!nameMapping.containsKey(userName)) {
                nameMapping.put(userName, sessionId);
                // 如果不存在这个用户就进行创建
                livingSessions.put(sessionId, session);
                sendWelComeTextToAll(sessionId, "欢迎用户 [" + userName + "] 来到聊天室!");
            } else {
                try {
                    session.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @OnMessage
    public void sendMessage(@PathParam("userName") String userName, Session session, String message) {
        sendWelComeTextToAll(session.getId(), "用户 [ " + userName + " ] : " + message + "");
    }

    @OnClose
    public void closeSession(@PathParam("userName") String userName, Session session) {
        // 获取会话ID
        final String sessionId = session.getId();
        livingSessions.remove(sessionId);
        nameMapping.remove(userName);
        sendWelComeTextToAll(sessionId, "用户 [" + userName + " ] 离开聊天室");
    }

    // 发送文本消息给所有在线的用户
    private void sendWelComeTextToAll(String sessionIdOwner, String welcomeText) {
        livingSessions.forEach((sessionId, session) -> {
            if (!sessionId.equals(sessionIdOwner)) {
                sendWelcomeText(session, welcomeText);
            }
        });
    }

    // 发送文本消息给某个用户
    private void sendWelcomeText(Session session, String welcomeText) {
        final RemoteEndpoint.Basic basicRemote = session.getBasicRemote();
        try {
            basicRemote.sendText(welcomeText);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
