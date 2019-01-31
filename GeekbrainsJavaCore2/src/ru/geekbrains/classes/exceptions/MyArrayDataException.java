package ru.geekbrains.classes.exceptions;

public class MyArrayDataException extends Exception {
    private int i;
    private  int k;
    public MyArrayDataException (int i, int k) {
        this.i = i;
        this.k = k;
    }

    public void getExceptionMsg(){
        System.out.println("Ошибка в приведении! строка "+i+" столбец "+k);
    }


}
