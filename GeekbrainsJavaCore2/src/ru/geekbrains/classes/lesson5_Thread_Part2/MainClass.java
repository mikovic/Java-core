package ru.geekbrains.classes.lesson5_Thread_Part2;


import java.util.concurrent.*;

public class MainClass {
    public static final int CARS_COUNT = 4;
    public static Semaphore semaphore = new Semaphore(2);
    public static CountDownLatch countDownLatch = new CountDownLatch(CARS_COUNT);
    public static CyclicBarrier cyclicBarrier= new CyclicBarrier((CARS_COUNT));

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
        }
        for (int i = 0; i < cars.length; i++) {
            executorService.execute(new Thread(cars[i]));
        }
        try {
            Thread.sleep(1400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }

}
