package ru.javawebinar.topjava.util;

import java.util.concurrent.atomic.AtomicInteger;

public class GeneratorID {
    private static final AtomicInteger id = new AtomicInteger(0);

    public static int getId(){
        return id.getAndIncrement();
    }
}
