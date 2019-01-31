package ru.geekbrains.classes.exceptions;

public class MyArraySizeException extends Exception  {
    public MyArraySizeException(){

    }
    public void getExceptionMsg (){
        System.out.println("Ошибка с размером массива");
    }

}
