package org.fizz_buzz.test.internaltest;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class TestBean {
    public TestBean() {
    }

    @Override
    public String toString() {
        return "Internal Test Bean";
    }
}
