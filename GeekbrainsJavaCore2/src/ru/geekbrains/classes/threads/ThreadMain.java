package ru.geekbrains.classes.threads;

import java.util.Arrays;

public class ThreadMain {
    static final  int  SIZE = 10000000;
    static final  int HALF = SIZE / 2;
    private Thread thr1;
    private Thread thr2;

    public static void main(String[] args) {

        float[] a1;
        float[] a2;
        float[] arr;
        arr = new float[SIZE];
        a1 = new float[HALF];
        a2 = new float[HALF];
        Timer timer = new Timer();
        Arrays.fill(arr, 1);
        ThreadMain threadMain = new ThreadMain();
        System.out.println("Start");
        new Thread(() -> threadMain.method1(arr, timer)).start();
        new Thread(() ->threadMain .method2(arr, a1, a2, timer)).start();

    }



    public synchronized void method1(float[] arr, Timer timer ) {
        timer.start();
        long a = System.currentTimeMillis();
        Arrays.fill(arr, formula(arr[0]));
        timer.stop();
        System.out.println("Первый метод. Заполнение массива." + timer);
    }

    public synchronized void method2(float[] arr, float[] a1, float[] a2, Timer timer) {
        timer.start();
        System.arraycopy(arr, 0, a1, 0, HALF);
        System.arraycopy(arr, HALF, a2, 0, HALF);
        timer.stop();
        System.out.println("Разбиение массива." + timer);
        thr1 = new Thread(new Runnable() {
            @Override
            public void run() {
                timer.start();
                Arrays.fill(a1, formula(a1[0]));
                timer.stop();
                System.out.println("Просчет 1 массива: " + timer);
            }

        });
        thr2 = new Thread(new Runnable() {
            @Override
            public void run() {
                timer.start();
                Arrays.fill(a2, formula(a2[0]));
                timer.stop();
                System.out.println("Просчет 2 массива: " + timer);
            }

        });
        thr1.start();
        try {
            thr1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thr2.start();
        try {
            thr2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        timer.start();
        System.arraycopy(a1, 0, arr, 0, HALF);
        System.arraycopy(a2, 0, arr, HALF, HALF);
        timer.stop();
        System.out.println("Склеивание 2-ух массивов: " + timer);

    }


    private static float formula(float x) {
        return (float) (x * Math.sin(0.2f + x / 5) * Math.cos(0.2f + x / 5) * Math.cos(0.4f + x / 2));


    }


}
