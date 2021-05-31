package org.fufeng.project.command.line;

import org.springframework.boot.ExitCodeGenerator;
import org.springframework.stereotype.Component;

@Component
public class MyExitCodeGenerator implements ExitCodeGenerator {
    @Override
    public int getExitCode() {
        return 1;
    }
}
