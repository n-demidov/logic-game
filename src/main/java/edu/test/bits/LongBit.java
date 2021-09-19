package edu.test.bits;

public class LongBit {

    protected long theValue;

    public static void main(String[] args) {

//        Boolean.parseBoolean(argument);


        LongBit longBit = new LongBit();

        // 1: right = 169
        longBit.setRight(169);
        soutAll(longBit);

        // 2: left = 999
        longBit.setLeft(999);
        soutAll(longBit);

        // 3: right = 13
        longBit.setRight(13);
        soutAll(longBit);

        // 4: right = 777
        longBit.setRight(777);
        soutAll(longBit);

        // 5: left = 9
        longBit.setLeft(9);
        soutAll(longBit);

        // 6: left = 3333
        longBit.setLeft(3333);
        soutAll(longBit);
    }

    public static void soutAll(LongBit longBit) {
        System.out.println("right: " + longBit.getRight());
        System.out.println("left: " + longBit.getLeft());
        System.out.println("long: " + longBit.theValue);
        System.out.println("");
    }

    public int getRight() {
//        return (int) (theValue & 0xFFFFFFFFL);
        return (int) (theValue);
    }

    public int getLeft() {
        return (int)(theValue >> 32);
    }

    public void setRight(int value) {
//        theValue ^= (theValue ^ (value & 0xFFFFFFFFL)) & 0xFFFFFFFFL;
        theValue = (theValue & 0xFFFFFFFF00000000L) | (value & 0xFFFFFFFFL);
    }

    public void setLeft(int value) {
//        theValue ^= (theValue ^ (((long) value) << 32)) & 0xFFFFFFFF00000000L;
        theValue = (theValue & 0xFFFFFFFFL) | ((value & 0xFFFFFFFFL) << 32);
    }

}
