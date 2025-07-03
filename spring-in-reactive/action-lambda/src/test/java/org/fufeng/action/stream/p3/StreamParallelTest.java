package org.fufeng.action.stream.p3;

import com.google.common.collect.Lists;
import org.fufeng.action.mvc.model.Student;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamParallelTest {
    public static AtomicInteger counter = new AtomicInteger();

    @Test
    public void simpleParallel() {
        List<Student> students = getLotsOfStudents();
        List<Student> studentList = students.parallelStream().peek(student -> counter.getAndIncrement()).collect(Collectors.toList());
//        System.out.println(studentList);
        System.out.println(students.size());
        System.out.println(counter);
    }

    @Test
    public void collectToListInParallel() {
        List<Student> students = getLotsOfStudents();
        students.parallelStream().collect(Collectors.toList());
    }

    @Test
    public void streamSeeSee() {
        Stream.Builder<Integer> builder = Stream.builder();
        for (int i = 0; i < 100; i++) {
            builder.accept(i);
        }
        Stream<Integer> stream = builder.build();
        stream.parallel()
//                .forEach(System.out::println);
//                .forEachOrdered(System.out::println);
                .collect(Collectors.toList());
//        System.out.println(list);
    }

    public List<Student> getStudentsData() {
        return Lists.newArrayList(
                Student.builder().name("A").className("Class-1").score(60).build(),
                Student.builder().name("B").className("Class-1").score(100).build(),
                Student.builder().name("C").className("Class-2").score(50).build(),
                Student.builder().name("D").className("Class-2").score(70).build(),
                Student.builder().name("E").className("Class-3").score(91).build(),
                Student.builder().name("F").className("Class-3").score(92).build()
        );
    }

    public List<Student> getLotsOfStudents() {
        return Lists.newArrayList(
                Student.builder().name("A").className("Class-1").score(60).build(),
                Student.builder().name("B").className("Class-1").score(100).build(),
                Student.builder().name("C").className("Class-2").score(50).build(),
                Student.builder().name("D").className("Class-2").score(70).build(),
                Student.builder().name("E").className("Class-3").score(91).build(),
                Student.builder().name("F").className("Class-3").score(92).build(),
                Student.builder().name("A").className("Class-1").score(60).build(),
                Student.builder().name("B").className("Class-1").score(100).build(),
                Student.builder().name("C").className("Class-2").score(50).build(),
                Student.builder().name("D").className("Class-2").score(70).build(),
                Student.builder().name("E").className("Class-3").score(91).build(),
                Student.builder().name("F").className("Class-3").score(92).build(),
                Student.builder().name("A").className("Class-1").score(60).build(),
                Student.builder().name("B").className("Class-1").score(100).build(),
                Student.builder().name("C").className("Class-2").score(50).build(),
                Student.builder().name("D").className("Class-2").score(70).build(),
                Student.builder().name("E").className("Class-3").score(91).build(),
                Student.builder().name("F").className("Class-3").score(92).build(),
                Student.builder().name("A").className("Class-1").score(60).build(),
                Student.builder().name("B").className("Class-1").score(100).build(),
                Student.builder().name("C").className("Class-2").score(50).build(),
                Student.builder().name("D").className("Class-2").score(70).build(),
                Student.builder().name("E").className("Class-3").score(91).build(),
                Student.builder().name("F").className("Class-3").score(92).build(),
                Student.builder().name("A").className("Class-1").score(60).build(),
                Student.builder().name("B").className("Class-1").score(100).build(),
                Student.builder().name("C").className("Class-2").score(50).build(),
                Student.builder().name("D").className("Class-2").score(70).build(),
                Student.builder().name("E").className("Class-3").score(91).build(),
                Student.builder().name("F").className("Class-3").score(92).build(),
                Student.builder().name("A").className("Class-1").score(60).build(),
                Student.builder().name("B").className("Class-1").score(100).build(),
                Student.builder().name("C").className("Class-2").score(50).build(),
                Student.builder().name("D").className("Class-2").score(70).build(),
                Student.builder().name("E").className("Class-3").score(91).build(),
                Student.builder().name("F").className("Class-3").score(92).build(),
                Student.builder().name("A").className("Class-1").score(60).build(),
                Student.builder().name("B").className("Class-1").score(100).build(),
                Student.builder().name("C").className("Class-2").score(50).build(),
                Student.builder().name("D").className("Class-2").score(70).build(),
                Student.builder().name("E").className("Class-3").score(91).build(),
                Student.builder().name("F").className("Class-3").score(92).build(),
                Student.builder().name("A").className("Class-1").score(60).build(),
                Student.builder().name("B").className("Class-1").score(100).build(),
                Student.builder().name("C").className("Class-2").score(50).build(),
                Student.builder().name("D").className("Class-2").score(70).build(),
                Student.builder().name("E").className("Class-3").score(91).build(),
                Student.builder().name("F").className("Class-3").score(92).build(),
                Student.builder().name("A").className("Class-1").score(60).build(),
                Student.builder().name("B").className("Class-1").score(100).build(),
                Student.builder().name("C").className("Class-2").score(50).build(),
                Student.builder().name("D").className("Class-2").score(70).build(),
                Student.builder().name("E").className("Class-3").score(91).build(),
                Student.builder().name("F").className("Class-3").score(92).build(),
                Student.builder().name("A").className("Class-1").score(60).build(),
                Student.builder().name("B").className("Class-1").score(100).build(),
                Student.builder().name("C").className("Class-2").score(50).build(),
                Student.builder().name("D").className("Class-2").score(70).build(),
                Student.builder().name("E").className("Class-3").score(91).build(),
                Student.builder().name("F").className("Class-3").score(92).build(),
                Student.builder().name("A").className("Class-1").score(60).build(),
                Student.builder().name("B").className("Class-1").score(100).build(),
                Student.builder().name("C").className("Class-2").score(50).build(),
                Student.builder().name("D").className("Class-2").score(70).build(),
                Student.builder().name("E").className("Class-3").score(91).build(),
                Student.builder().name("F").className("Class-3").score(92).build(),
                Student.builder().name("A").className("Class-1").score(60).build(),
                Student.builder().name("B").className("Class-1").score(100).build(),
                Student.builder().name("C").className("Class-2").score(50).build(),
                Student.builder().name("D").className("Class-2").score(70).build(),
                Student.builder().name("E").className("Class-3").score(91).build(),
                Student.builder().name("F").className("Class-3").score(92).build(),
                Student.builder().name("A").className("Class-1").score(60).build(),
                Student.builder().name("B").className("Class-1").score(100).build(),
                Student.builder().name("C").className("Class-2").score(50).build(),
                Student.builder().name("D").className("Class-2").score(70).build(),
                Student.builder().name("E").className("Class-3").score(91).build(),
                Student.builder().name("F").className("Class-3").score(92).build(), Student.builder().name("A").className("Class-1").score(60).build(),
                Student.builder().name("B").className("Class-1").score(100).build(),
                Student.builder().name("C").className("Class-2").score(50).build(),
                Student.builder().name("D").className("Class-2").score(70).build(),
                Student.builder().name("E").className("Class-3").score(91).build(),
                Student.builder().name("F").className("Class-3").score(92).build(), Student.builder().name("A").className("Class-1").score(60).build(),
                Student.builder().name("B").className("Class-1").score(100).build(),
                Student.builder().name("C").className("Class-2").score(50).build(),
                Student.builder().name("D").className("Class-2").score(70).build(),
                Student.builder().name("E").className("Class-3").score(91).build(),
                Student.builder().name("F").className("Class-3").score(92).build(), Student.builder().name("A").className("Class-1").score(60).build(),
                Student.builder().name("B").className("Class-1").score(100).build(),
                Student.builder().name("C").className("Class-2").score(50).build(),
                Student.builder().name("D").className("Class-2").score(70).build(),
                Student.builder().name("E").className("Class-3").score(91).build(),
                Student.builder().name("F").className("Class-3").score(92).build(), Student.builder().name("A").className("Class-1").score(60).build(),
                Student.builder().name("B").className("Class-1").score(100).build(),
                Student.builder().name("C").className("Class-2").score(50).build(),
                Student.builder().name("D").className("Class-2").score(70).build(),
                Student.builder().name("E").className("Class-3").score(91).build(),
                Student.builder().name("F").className("Class-3").score(92).build(), Student.builder().name("A").className("Class-1").score(60).build(),
                Student.builder().name("B").className("Class-1").score(100).build(),
                Student.builder().name("C").className("Class-2").score(50).build(),
                Student.builder().name("D").className("Class-2").score(70).build(),
                Student.builder().name("E").className("Class-3").score(91).build(),
                Student.builder().name("F").className("Class-3").score(92).build(), Student.builder().name("A").className("Class-1").score(60).build(),
                Student.builder().name("B").className("Class-1").score(100).build(),
                Student.builder().name("C").className("Class-2").score(50).build(),
                Student.builder().name("D").className("Class-2").score(70).build(),
                Student.builder().name("E").className("Class-3").score(91).build(),
                Student.builder().name("F").className("Class-3").score(92).build(), Student.builder().name("A").className("Class-1").score(60).build(),
                Student.builder().name("B").className("Class-1").score(100).build(),
                Student.builder().name("C").className("Class-2").score(50).build(),
                Student.builder().name("D").className("Class-2").score(70).build(),
                Student.builder().name("E").className("Class-3").score(91).build(),
                Student.builder().name("F").className("Class-3").score(92).build(), Student.builder().name("A").className("Class-1").score(60).build(),
                Student.builder().name("B").className("Class-1").score(100).build(),
                Student.builder().name("C").className("Class-2").score(50).build(),
                Student.builder().name("D").className("Class-2").score(70).build(),
                Student.builder().name("E").className("Class-3").score(91).build(),
                Student.builder().name("F").className("Class-3").score(92).build(), Student.builder().name("A").className("Class-1").score(60).build(),
                Student.builder().name("B").className("Class-1").score(100).build(),
                Student.builder().name("C").className("Class-2").score(50).build(),
                Student.builder().name("D").className("Class-2").score(70).build(),
                Student.builder().name("E").className("Class-3").score(91).build(),
                Student.builder().name("F").className("Class-3").score(92).build(), Student.builder().name("A").className("Class-1").score(60).build(),
                Student.builder().name("B").className("Class-1").score(100).build(),
                Student.builder().name("C").className("Class-2").score(50).build(),
                Student.builder().name("D").className("Class-2").score(70).build(),
                Student.builder().name("E").className("Class-3").score(91).build(),
                Student.builder().name("F").className("Class-3").score(92).build(), Student.builder().name("A").className("Class-1").score(60).build(),
                Student.builder().name("B").className("Class-1").score(100).build(),
                Student.builder().name("C").className("Class-2").score(50).build(),
                Student.builder().name("D").className("Class-2").score(70).build(),
                Student.builder().name("E").className("Class-3").score(91).build(),
                Student.builder().name("F").className("Class-3").score(92).build(), Student.builder().name("A").className("Class-1").score(60).build(),
                Student.builder().name("B").className("Class-1").score(100).build(),
                Student.builder().name("C").className("Class-2").score(50).build(),
                Student.builder().name("D").className("Class-2").score(70).build(),
                Student.builder().name("E").className("Class-3").score(91).build(),
                Student.builder().name("F").className("Class-3").score(92).build(), Student.builder().name("A").className("Class-1").score(60).build(),
                Student.builder().name("B").className("Class-1").score(100).build(),
                Student.builder().name("C").className("Class-2").score(50).build(),
                Student.builder().name("D").className("Class-2").score(70).build(),
                Student.builder().name("E").className("Class-3").score(91).build(),
                Student.builder().name("F").className("Class-3").score(92).build(), Student.builder().name("A").className("Class-1").score(60).build(),
                Student.builder().name("B").className("Class-1").score(100).build(),
                Student.builder().name("C").className("Class-2").score(50).build(),
                Student.builder().name("D").className("Class-2").score(70).build(),
                Student.builder().name("E").className("Class-3").score(91).build(),
                Student.builder().name("F").className("Class-3").score(92).build(), Student.builder().name("A").className("Class-1").score(60).build(),
                Student.builder().name("B").className("Class-1").score(100).build(),
                Student.builder().name("C").className("Class-2").score(50).build(),
                Student.builder().name("D").className("Class-2").score(70).build(),
                Student.builder().name("E").className("Class-3").score(91).build(),
                Student.builder().name("F").className("Class-3").score(92).build(), Student.builder().name("A").className("Class-1").score(60).build(),
                Student.builder().name("B").className("Class-1").score(100).build(),
                Student.builder().name("C").className("Class-2").score(50).build(),
                Student.builder().name("D").className("Class-2").score(70).build(),
                Student.builder().name("E").className("Class-3").score(91).build(),
                Student.builder().name("F").className("Class-3").score(92).build(), Student.builder().name("A").className("Class-1").score(60).build(),
                Student.builder().name("B").className("Class-1").score(100).build(),
                Student.builder().name("C").className("Class-2").score(50).build(),
                Student.builder().name("D").className("Class-2").score(70).build(),
                Student.builder().name("E").className("Class-3").score(91).build(),
                Student.builder().name("F").className("Class-3").score(92).build(), Student.builder().name("A").className("Class-1").score(60).build(),
                Student.builder().name("B").className("Class-1").score(100).build(),
                Student.builder().name("C").className("Class-2").score(50).build(),
                Student.builder().name("D").className("Class-2").score(70).build(),
                Student.builder().name("E").className("Class-3").score(91).build(),
                Student.builder().name("F").className("Class-3").score(92).build(),
                Student.builder().name("G").className("Class-3").score(93).build()
        );
    }
}
