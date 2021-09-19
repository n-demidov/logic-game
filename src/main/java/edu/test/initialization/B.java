package edu.test.initialization;

import java.util.HashMap;
import java.util.Map;

public class B {

    private static final B b = new B();

    static {
        b.start();
    }

    public static final String CONST_1 = b.map.get("b");
//    public static String CONST_1;

    private Map<String, String> map = new HashMap<>();

    public void start() {
//        B.CONST_1 = "init B class";
        map.put("b", "init B class");
    }

}
