package org.fizz_buzz.test;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

public class TestBean {
    private int id = 1;

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Test Bean";
    }
}
