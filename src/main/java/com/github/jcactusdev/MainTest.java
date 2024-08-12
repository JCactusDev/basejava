package com.github.jcactusdev;

import com.github.jcactusdev.util.StreamUtil;

import java.util.ArrayList;
import java.util.List;

public class MainTest {
    public static void main(String[] args) {
        int[] values = {1, 2, 3, 0};
        System.out.println(StreamUtil.minValue(values));

        List<Integer> integers = new ArrayList<>(List.of(1, 2, 3, 4, 5, 7));
        System.out.println(StreamUtil.oddOrEven(integers));

    }
}
