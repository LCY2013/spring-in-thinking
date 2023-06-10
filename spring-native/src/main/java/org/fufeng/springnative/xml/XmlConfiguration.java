package org.fufeng.springnative.xml;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.lang.Nullable;

import java.util.function.Supplier;

// <1>
@Configuration
@ImportResource("app.xml")
class XmlConfiguration {

}

// <2>
class MessageProducer implements Supplier<String> {

    @Override
    public String get() {
        return "Hello, world!";
    }

}

// <3>
class XmlLoggingApplicationListener implements ApplicationListener<ApplicationReadyEvent> {

    @Nullable
    private MessageProducer producer;

    public void setProducer(@Nullable MessageProducer producer) {
        this.producer = producer;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        var message = this.producer.get();
        System.out.println("the message is " + message);
    }

}
