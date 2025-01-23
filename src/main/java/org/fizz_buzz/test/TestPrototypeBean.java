package org.fizz_buzz.test;

import java.util.concurrent.atomic.AtomicInteger;

public class TestPrototypeBean {
    static volatile AtomicInteger idCounter = new AtomicInteger(1);

    private final int id;

    public TestPrototypeBean() {
        id = idCounter.getAndIncrement();
    }

    @Override
    public String toString() {
        return "TestPrototypeBean [id=" + id + "]";
    }
}
