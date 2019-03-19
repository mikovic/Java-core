package ru.geekbrains.classes.lesson5_Thread_Part2;

import java.util.concurrent.CyclicBarrier;

public class Car implements Runnable {
    private static int CARS_COUNT;

    static {
        CARS_COUNT = 0;
    }


    private Race race;
    private int speed;
    private String name;

    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");
            MainClass.cyclicBarrier.await();
            Thread.sleep(500);

        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            switch (i) {
                case 0:
                    race.getStages().get(i).go(this);
                    break;
                case 1:
                    try {
                        MainClass.semaphore.acquire();
                        race.getStages().get(i).go(this);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    MainClass.semaphore.release();
                    break;
                case 2:
                    race.getStages().get(i).go(this);
                    MainClass.countDownLatch.countDown();
                    break;
            }
        }
    }

}
