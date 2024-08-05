package com.example.simplebank.demo.utils;

import java.util.List;
import java.util.Random;

public class RandomUtils {
    private static final Random RANDOM = new Random();

    public static <T> T getRandomItem(List<T> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("The list must not be null or empty");
        }
        int index = RANDOM.nextInt(list.size());
        return list.get(index);
    }
}
