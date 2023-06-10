package org.fufeng.springnative.factorybeans;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FactoryBeansConfiguration {

    @Bean
    AnimalFactoryBean animalFactoryBean() {
        return new AnimalFactoryBean(true, false);
    }

    @Bean
    ApplicationListener<ApplicationReadyEvent> factoryBeanListener(Animal animal) {
        return event -> animal.speak();
    }

}

class AnimalFactoryBean implements FactoryBean<Animal> {

    private final boolean likesYarn, likesFrisbees;

    AnimalFactoryBean(boolean likesYarn, boolean likesFrisbees) {
        this.likesYarn = likesYarn;
        this.likesFrisbees = likesFrisbees;
    }


    @Override
    public Animal getObject() throws Exception {
        return likesYarn && !likesFrisbees ? new Cat() : new Dog();
    }

    @Override
    public Class<?> getObjectType() {
        return Animal.class;
    }
}

interface Animal {
    void speak();
}

class Dog implements Animal {
    @Override
    public void speak() {
        System.out.println("woof");
    }
}

class Cat implements Animal {
    @Override
    public void speak() {
        System.out.println("meow");
    }
}

