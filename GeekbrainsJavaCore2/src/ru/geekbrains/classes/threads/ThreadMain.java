package ru.geekbrains.classes.threads;

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
        for (int i = 0; i < SIZE; i++) {
            arr[i] = 1;

        }
        ThreadMain threadMain = new ThreadMain();
        System.out.println("Start");
        new Thread(() -> threadMain.method1(arr)).start();
        new Thread(() ->threadMain .method2(arr, a1, a2)).start();

    }



    public synchronized void method1(float[] arr) {
        long a = System.currentTimeMillis();
        for (int i = 0; i < SIZE; i++) {
            arr[i] = formula(arr[i]);

        }
        a = System.currentTimeMillis() - a;
        System.out.println("Время выполнения первого метода: " + a);
    }

    public synchronized void method2(float[] arr, float[] a1, float[] a2) {
        long a = System.currentTimeMillis();
        System.arraycopy(arr, 0, a1, 0, HALF);
        System.arraycopy(arr, HALF, a2, 0, HALF);
        a = System.currentTimeMillis() - a;
        System.out.println("Время разбивки массива: " + a);
        thr1 = new Thread(new Runnable() {
            @Override
            public void run() {
                long a = System.currentTimeMillis();
                for (int i = 0; i < HALF; i++) {
                    a1[i] = formula(a1[i]);

                }
                a = System.currentTimeMillis() - a;
                System.out.println("Время просчета 1 массива: " + a);
            }

        });
        thr2 = new Thread(new Runnable() {
            @Override
            public void run() {
                long a = System.currentTimeMillis();
                for (int i = 0; i < HALF; i++) {
                    a2[i] = formula(a2[i]);

                }
                a = System.currentTimeMillis() - a;
                System.out.println("Время просчета 2 массива: " + a);
            }

        });
        thr1.start();
        thr2.start();
        a = System.currentTimeMillis();
        System.arraycopy(a1, 0, arr, 0, HALF);
        System.arraycopy(a2, 0, arr, HALF, HALF);

        a = System.currentTimeMillis() - a;
        System.out.println("Время разбивки массива: " + a);

    }


    private static float formula(float x) {
        return (float) (x * Math.sin(0.2f + x / 5) * Math.cos(0.2f + x / 5) * Math.cos(0.4f + x / 2));


    }


}
