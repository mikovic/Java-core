package ru.geekbrains.classes.exceptions;

public class MyArrayDataException extends Exception {
    private int i;
    private int k;
    private String value;

    public MyArrayDataException (int i, int k, String value) {
        super("Ошибка в приведении! строка "+i+" столбец "+k+". Значение: " +value);
        this.i = i;
        this.k = k;
        this.value = value;

    }

    public int getK() {
        return k;
    }

    public int getI() {
        return i;
    }
}
