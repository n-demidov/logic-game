package edu.test;

public class ArsTask {

    public static void main(String[] args) {
        ArsTask arsTask = new ArsTask();

    }

    /**
     * Находит полож. и отриц. значения наиболее ближе к нулю.
     * Возвращает объект Pair, где ключ - полож., а value - отриц. значения.
     * @param array
     * @return
     */
    public Pair<Integer, Integer> find(int[] array) {
        Pair<Integer, Integer> result = new Pair<>(Integer.MAX_VALUE, Integer.MIN_VALUE);
        int size = array.length;

        int i = 0;
        while (i < size) {
            int val = array[i];

            if (val > 0 && val < result.key) {
                result.key = val;
            } else if (val < 0 && val > result.value) {
                result.value = val;
            }

            i++;
        }

        return result;
    }

    public class Pair<K,V> {
        int key;
        int value;

        public Pair(int key, int value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Pair{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }
    }

}
