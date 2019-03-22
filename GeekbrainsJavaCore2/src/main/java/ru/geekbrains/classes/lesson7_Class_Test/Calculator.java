package ru.geekbrains.classes.lesson7_Class_Test;



public class Calculator {


    public Calculator() {
    }

    @BeforeSuite
    public void firstStep() {
        System.out.println("Начало");
    }

    @Test(priority = 4)
    public void sum(double x, double y) {
        System.out.println(x + " + " + y + " = " + (x + y));
    }
    @Test(priority = 5)
    public void dif(double x, double y) {
        System.out.println(x + "-" + y + " = " + (x - y));
    }
    @Test(priority = 3)
    public void multiply(double x, double y) {
        System.out.println(x + " * " + y + " = " + x * y);
    }
    @Test(priority = 10)
    public void divide(double x, double y) {
        System.out.println(x + " / " + y + " = " + x / y);
    }
    @Test
    public void sqrt(double x) {
        System.out.println("Корень квадратный из числа " + x + " = " + Math.sqrt(x));
    }
    @Test
    public void sin(double x) {
        System.out.println("sin " + x + " радиан = " + Math.sin(x));
    }
    public static void start(Class cls){

    }


    @AfterSuite
    public void lastStep() {
        System.out.println("Все!");
    }
}
