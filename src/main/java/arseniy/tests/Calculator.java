package arseniy.tests;

public class Calculator {

    public int sum(int a, int b) {
        return a + b;
    }

    /**
     * Devide only positive numbers
     * @param a
     * @param b
     * @return
     */
    public int devide(int a, int b) {
        if (a < 0 || b < 0) {
            throw new IllegalArgumentException();
        }

        return a / b;
    }

}
