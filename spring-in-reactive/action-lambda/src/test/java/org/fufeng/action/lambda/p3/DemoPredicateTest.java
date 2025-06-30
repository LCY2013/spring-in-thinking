package org.fufeng.action.lambda.p3;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.function.Predicate;

@Slf4j
public class DemoPredicateTest {

    @Test
    public void SimplePredicate() {
        Predicate<Integer> isAdult = age -> (age > 18);
        log.info(String.valueOf(isAdult.test(20)));
        log.info(String.valueOf(isAdult.test(16)));
    }

    @Test
    public void AndPredicate() {
        Predicate<Integer> isAdult = new Predicate<Integer>() {
            @Override
            public boolean test(Integer age) {
                log.info("we are young once");
                return age > 18;
            }
        };
        Predicate<Integer> isRetired = age -> {
            log.info("yes, pension");
            return age > 70;
        };

        log.info(String.valueOf(isAdult.and(isRetired).test(25)));
//        log.info(String.valueOf(isRetired.and(isAdult).test(25)));
    }

    @Test
    public void OrPredicate() {
        Predicate<Integer> cannotRead = age -> (age < 4);
        Predicate<Integer> cannotCode = age -> (age > 99);
        log.info("Should quit coding at 35? {}",
                cannotRead.or(cannotCode).test(35));
    }

    @Test
    public void NegatePredicate(){
        Predicate<Integer> isAdult = age -> (age >18);
        log.info(String.valueOf(isAdult.negate().test(16)));
    }

    @Test
    public void compositePredicate(){
        Predicate<Integer> cannotRead = age -> (age < 4);
        Predicate<Integer> cannotCode = age -> (age > 99);

        //composite them together
        Predicate<Integer> compositePredicate = cannotRead
                .or(cannotCode)
                .negate();
        //now you can pass this composite predicate around in your program
        log.info("Should quit coding at 35? {}", compositePredicate.test(35));
    }

}
