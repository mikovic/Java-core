package ru.geekbrains.classes.lesson5_Thread_Part2;

public class Road extends Stage {
    public Road(int length) {
        this.length = length;
        this.description = "Дорога " + length + " метров";
    }
    @Override
    public void go(Car c) {
        try {
            System.out.println(c.getName() + " начал этап: " + description);
            Thread.sleep(length / c.getSpeed() * 1000);
            System.out.println(c.getName() + " закончил этап: " + description);
            if(MainClass.CARS_COUNT == MainClass.countDownLatch.getCount()) {
                if(MainClass.atomicBoolean.compareAndSet(false, true)){
                    MainClass.transporant = c.getName() + " WIN!!!";
                }
            }
            MainClass.countDownLatch.countDown();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
