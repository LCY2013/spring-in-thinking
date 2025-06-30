package org.fufeng.action.stream.p2;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamNIOTest {

    @Test
    public void FilesLinesTest() {
        Path path = new File(getClass().getResource("/stream/nio_datasource_1.txt").getFile()).toPath();
        try (Stream<String> content = Files.lines(path, Charset.defaultCharset())) {
            List<String> orderedList = content.map(String::toUpperCase).sorted().collect(Collectors.toList());
            System.out.println(orderedList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void FilesWalkTest() {
        Path rootPath = new File(getClass().getResource("/stream/a").getFile()).toPath();
        try (Stream<Path> matched = Files.walk(rootPath, 3)) {
            List<Path> matchedPathList = matched.collect(Collectors.toList());
            System.out.println(matchedPathList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void FilesFindTest() throws IOException {
        Path rootPath = new File(getClass().getResource("/stream/a").getFile()).toPath();
        try (Stream<Path> matched = Files.find(rootPath, 3, (path, attr) -> path.endsWith("bar.txt"))) {
            List<Path> matchedPathList = matched.collect(Collectors.toList());
            System.out.println(matchedPathList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
