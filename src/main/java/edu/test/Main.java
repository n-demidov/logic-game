package edu.test;

import edu.test.dtos.CatDto;
import edu.test.enums.Colors;
import edu.test.initialization.A;
import edu.test.initialization.B;
import edu.test.initialization.C;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        System.out.println("double test");

        double d = 0;

        System.out.println(0 == d);


        String s1 = "s";
        String s2 = "";
        System.out.println(s1 + s2);



        int numKeys = 3000;
        String[] keys;
        keys = new String[numKeys];
        for (int i = 0; i < numKeys; i++) {
//            keys[i] = RandomStringUtils.randomAlphanumeric(16);
        }



        Colors.values();

        System.out.println("==");
        System.out.println(13 / 5);
        System.out.println("==");

//        C c = new C();
//
//        A a = new A();
//
//        B b = new B();
//        b.start();

//        System.out.println(A.CONST_1);
//        System.out.println(B.CONST_1);


//        bool();

//        testIntern();
//        ooMIntern();

//        System.out.println("abcd" == new String("ab" + "cd"));
//        "abc".intern();
//
//        Map<String, CatDto> map = new HashMap<>();
//        CatDto cat = new CatDto("Murzik", 19);
//
//        map.put("1", cat);
//        map.put("2", cat);
//        map.put("3", cat);
//
//        System.out.println(map);
    }

    private static void ooMIntern() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 1_000_000_000; i++) {
            String s = "_________________________________" + i;
//            String s = new String("_________________________________").intern();
            list.add(s);
        }
    }

    private static void testIntern() {
        System.out.println("version" == "version".intern());
        System.out.println("ver" + "sion" == "version".intern());
    }

    private static void bool() {
        Boolean initB = null;

        boolean b = initB != null ? initB : false;

        int syncMode = 1;
        boolean resultB = isB(syncMode);

    }

    private static boolean isB(int syncMode) {
        switch (syncMode) {
            case 1:
            case 2:
                return true;
            case 3:
                return false;
        }

        throw new IllegalArgumentException();
    }

}
