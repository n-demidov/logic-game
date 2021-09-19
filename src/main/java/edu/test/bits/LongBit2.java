package edu.test.bits;

public class LongBit2 {

    public static void main(String[] args) {
        long l = 0;
        int x = 169;
        int y = 15;

//        l = (((long)x) << 32) | (y & 0xffffffffL);


        l ^= ((l ^ (y & 0xFFFFFFFFL)) & 0xFFFFFFFFL);
        l ^= (((long)x) << 32);

        x = (int)(l >> 32);
        y = (int)l;

        System.out.println("x: " + x);
        System.out.println("y: " + y);
        System.out.println("l: " + l);
    }

}
