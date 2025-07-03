package org.fufeng.action.stream.p1;

import com.google.common.collect.Lists;
import org.fufeng.action.mvc.model.Stock;
import org.fufeng.action.mvc.model.Student;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamOperationTest {
    @Test
    public void streamForEach() {
        List<Stock> stocks = Lists.newArrayList(
                Stock.builder().name("APPLE").symbol("APPL").build(),
                Stock.builder().name("Amazon").symbol("AMZN").build(),
                Stock.builder().name("Starbucks").symbol("SBUX").build()
        );
        stocks.stream().forEach(stock -> stock.setSymbol(stock.getSymbol().toLowerCase()));
        System.out.println(stocks);

        //directly on Iterable and Map
        stocks.forEach(stock -> stock.setSymbol(stock.getSymbol() + "Iterable"));
        System.out.println(stocks);
        Map<String, Object> map = new HashMap();
        map.forEach((k, v)->{});
    }

    @Test
    public void streamMap() {
        Stream<String> stream = Stream.of("foo", "bar", "baz");
        Stream<Stock> stockStream = stream.map(str -> Stock.builder().name(str).build());
    }

    @Test
    public void streamCollectToList() {
        Stream<String> stream = Stream.of("foo", "bar", "baz");
        Stream<Stock> stockStream = stream.map(str -> Stock.builder().name(str).build());
        List<Stock> stocks = stockStream.collect(Collectors.toList());
    }

    @Test
    public void streamCollectJoining() {
        Stream<String> stream = Stream.of("foo", "bar", "baz");
        String result = stream.collect(Collectors.joining("|"));
        System.out.println(result);
    }

    @Test
    public void streamCollectGroupBy() {
        List<Student> students = getStudentsData();
        //group student by class
        Map<String, List<Student>> byClass =
                students.stream().collect(Collectors.groupingBy(Student::getClassName));
        System.out.println(byClass);
    }

    @Test
    public void streamCollectGroupByWithCustomizedDownstream() {
        List<Student> students = getStudentsData();
        //sum student score by class
        Map<String, Integer> sumScoreByClass =
                students.stream().collect(
                        Collectors.groupingBy(
                                Student::getClassName,
                                Collectors.summingInt(Student::getScore)
                        )
                );
        System.out.println(sumScoreByClass);
        //get student with highest score for each class
        Map<String, Optional<Student>> highestScoreByClass = students.stream().collect(
                Collectors.groupingBy(
                        Student::getClassName,
                        Collectors.maxBy(Comparator.comparing(Student::getScore))
                )
        );
        System.out.println(highestScoreByClass);
    }

    @Test
    public void streamFilter() {
        List<Student> students = getStudentsData();
        Stream<Student> scoreOver80Stream = students.stream().filter(student -> student.getScore() > 80);
        System.out.println(scoreOver80Stream.collect(Collectors.toList()));
    }

    @Test
    public void streamFindFirst() {
        List<Student> students = getStudentsData();
        Student firstGuy = students
                .stream()
                .filter(student -> student.getScore() > 80)
                .findFirst()
                .orElse(new Student());
        System.out.println(firstGuy);
    }

    @Test
    public void streamPeek() {
        List<Student> students = getStudentsData();
        students
                .stream()
                .peek(student -> student.setScore(student.getScore() + 10))
                .peek(System.out::println);
//                .collect(Collectors.toList());
    }

    @Test
    public void streamFlatMap() {
        String paragraph = "this is a demo paragraph to calculate char occurrences";
        String[] words = paragraph.split(" ");
        Map<String, Long> occurrence = Arrays.stream(words)
                .map(str -> str.split(""))
                .flatMap(strAarray -> Arrays.stream(strAarray))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println(occurrence);
    }

    public List<Student> getStudentsData() {
        return Lists.newArrayList(
                Student.builder().name("A").className("Class-1").score(60).build(),
                Student.builder().name("B").className("Class-1").score(100).build(),
                Student.builder().name("C").className("Class-2").score(50).build(),
                Student.builder().name("D").className("Class-2").score(70).build(),
                Student.builder().name("E").className("Class-3").score(91).build(),
                Student.builder().name("F").className("Class-3").score(92).build(),
                Student.builder().name("G").className("Class-3").score(93).build()
        );
    }
}
