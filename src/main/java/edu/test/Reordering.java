package edu.test;

public class Reordering {

    volatile int x = 5;
    volatile int y = 0;

    public void write() {
        x = 1;
        y = 2;
    }

    public void read() {
        if (y == 2){
            if (x == 0) {
                throw new IllegalStateException("asd");
            }

            x = 0;
            y = 0;
        }
    }

    public static void main(String[] args) {

        final Reordering reordering = new Reordering();

        Thread thread = new Thread() {
            public void run() {
                while (true) {
                    reordering.write();
                }
            }
        };

        thread.setDaemon(true);
        thread.start();

        while (true) {
            reordering.read();
        }
    }

}
