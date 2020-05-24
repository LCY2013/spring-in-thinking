package org.lcydream.project.i18n;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @program: spring-in-thinking
 * @description: 消息格式化示例 {@link MessageFormat}
 * @author: <a href="https://github.com/lcy2013">MagicLuo</a>
 * @create: 2020-05-24 15:30
 */
public class MessageFormatDemo {

    public static void main(String[] args) {
        int planet = 7;
        String event = "a disturbance in the Force";

        String pattern = "At {1,time,long} on {1,date,full}, there was {2} on planet {0,number,integer}.";
        MessageFormat messageFormat = new MessageFormat(pattern);
        String result = messageFormat.format(
                new Object[]{planet, new Date(), event});
        System.out.println(result);

        //利用apply重置MessageFormat 的pattern
        pattern = "fufeng saying to : {0},{1},{2}";
        messageFormat.applyPattern(pattern);
        result = messageFormat.format(new Object[]{"he", "ha"});
        System.out.println(result);

        //重新给定Locale
        messageFormat.setLocale(Locale.ENGLISH);
        pattern = "At {1,time,long} on {1,date,full}, there was {2} on planet {0,number,integer}.";
        messageFormat.applyPattern(pattern);
        result = messageFormat.format(new Object[]{planet, new Date(), event});
        System.out.println(result);

        //重新给定format
        messageFormat.setFormatByArgumentIndex(1,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:sss"));
        result = messageFormat.format(new Object[]{planet, new Date(), event});
        System.out.println(result);

    }

}
