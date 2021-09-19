package edu.test;

import java.util.function.BiFunction;

public class ATest {

    public static void main(String[] args) {
        BiFunction<Integer, Integer, Integer> bf = (Integer a, Integer b) -> {
            return a + b;
        };

        Integer result = bf.apply(10, 3);

        System.out.println("result: " + result);
    }

}
