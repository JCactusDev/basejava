package com.github.jcactusdev.util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.toList;

public class StreamUtil {

    public static int minValue(int[] values) {
        return Arrays
                .stream(values)
                .distinct()
                .min()
                .orElseThrow();

    }

    public static List<Integer> oddOrEven(List<Integer> integers) {
        Map<Boolean, List<Integer>> map = integers
                .stream()
                .collect(
                        partitioningBy(i -> i % 2 == 0, toList())
                );
        return map.get(map.get(false).size() % 2 != 0);
    }
}
