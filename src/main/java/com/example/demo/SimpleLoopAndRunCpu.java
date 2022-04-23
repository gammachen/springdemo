package com.example.demo;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SimpleLoopAndRunCpu {
    private static Executor executors = Executors.newFixedThreadPool(5);
    private static Object lock = new Object();

    public static void main(String[] args) {
        /**
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (true) {
                    i++;
                }
            }
        };

        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(runnable);
            thread.start();
        }
         **/

        MyTask myTask1 = new MyTask();
        MyTask myTask2 = new MyTask();
        MyTask myTask3 = new MyTask();
        executors.execute(myTask1);
        executors.execute(myTask2);
        executors.execute(myTask3);
    }

    static class MyTask implements Runnable {

        @Override
        public void run() {
             synchronized (lock) {
                calculate();
             }
        }

        private void calculate() {
            int i = 0;
            while (true) {
                i++;
            }
        }
    }
}
