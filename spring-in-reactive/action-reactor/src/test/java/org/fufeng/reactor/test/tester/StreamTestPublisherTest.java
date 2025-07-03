package org.fufeng.reactor.test.tester;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;
import reactor.test.publisher.TestPublisher;

public class StreamTestPublisherTest {
    @Test
    public void simpleTestPublisher() {
        TestPublisher<String> testPublisher = TestPublisher.<String>create()
                .emit("foo", "bar", "baz")
                .error(new Exception());
//                .next("foo", "bar")
//                .next("baz")
//                .complete();
    }

    @Test
    public void useTestPublisher() {
        TestPublisher<String> testPublisher = TestPublisher.create();
        StepVerifier.create(testPublisher.flux().map(String::toUpperCase).log())
                .then(() -> testPublisher.emit("foo", "bar"))
                .expectNext("FOO", "BAR")
                .verifyComplete();
    }

    @Test
    public void createNonCompliantTestPublisher() {
        TestPublisher<String> testPublisher = TestPublisher.createNoncompliant(TestPublisher.Violation.ALLOW_NULL);
    }
}
