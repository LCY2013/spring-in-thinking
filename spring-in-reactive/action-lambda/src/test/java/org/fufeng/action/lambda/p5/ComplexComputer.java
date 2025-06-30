package org.fufeng.action.lambda.p5;

import java.security.GeneralSecurityException;

public class ComplexComputer {
    public String encrypt(String input) throws GeneralSecurityException, InterruptedException {
        Thread.sleep(1000L);
        return "encoded";
    }
}
