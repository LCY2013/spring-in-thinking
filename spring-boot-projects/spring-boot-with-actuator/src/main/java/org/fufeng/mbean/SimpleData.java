/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: spring-in-thinking
 * @Author : <a href="https://github.com/lcy2013">MagicLuo</a>
 * @date : 2020-08-20
 * @version : 1.0.0-RELEASE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.fufeng.mbean;

import javax.management.*;

import java.util.concurrent.atomic.AtomicLong;

import static javax.management.AttributeChangeNotification.ATTRIBUTE_CHANGE;

/**
 * @program: spring-in-thinking
 * @description: TODO
 * @author: <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @create: 2020-08-20
 */
public class SimpleData extends NotificationBroadcasterSupport implements SimpleDataMBean,
        NotificationListener, NotificationFilter {

    private String data;

    private static final AtomicLong sequenceNumber = new AtomicLong();

    public SimpleData() {
        this.addNotificationListener(this, this, null);
    }

    @Override
    public String getData() {
        return data;
    }

    @Override
    public String displayData() {
        return data;
    }

    @Override
    public void setData(String data) {
        String oldData = this.data;
        this.data = data;
        Notification notification = new AttributeChangeNotification(this,
                sequenceNumber.incrementAndGet(),
                System.currentTimeMillis(),
                "Data has been changed from " + oldData + " to " + data,
                "data",
                String.class.getName(),
                oldData,
                data
        );
        sendNotification(notification);
    }

    /**
     * 处理通知/事件
     *
     * @param notification
     * @param handback
     */
    @Override
    public void handleNotification(Notification notification, Object handback) {

        AttributeChangeNotification attributeChangeNotification = (AttributeChangeNotification) notification;

        String oldData = (String) attributeChangeNotification.getOldValue();

        String newData = (String) attributeChangeNotification.getNewValue();

        System.out.printf("The notification of data's attribute  - old data : %s , new data : %s \n", oldData, newData);

    }


    @Override
    public boolean isNotificationEnabled(Notification notification) {

        // 直关心 AttributeChangeNotification 通知，并且仅限于字段/属性名称为 "data"
        if (AttributeChangeNotification.class.isAssignableFrom(notification.getClass())) {

            AttributeChangeNotification attributeChangeNotification = (AttributeChangeNotification) notification;

            return "data".equals(attributeChangeNotification.getAttributeName());
        }
        return false;
    }

    /**
     * 暴露通知信息
     *
     * @return
     */
    public MBeanNotificationInfo[] getNotificationInfo() {
        return new MBeanNotificationInfo[]{
                new MBeanNotificationInfo(new String[]{ATTRIBUTE_CHANGE}, "Data Change Notification",
                        "数据改变通知")
        };
    }
}
