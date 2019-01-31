package ru.geekbrains.classes.exceptions;

public class TestException {
    public static void main(String[] args) {

        String[][] array = {
                {"4", "10", "15", "25"},
                {"40", "11", "15", "30"},
                {"55", "10", "10", "2"},
                {"0", "10", "11", "22"}

        };

        try {
            processingArray(array, 4);
        } catch (MyArraySizeException e) {
            e.getExceptionMsg();
        } catch (MyArrayDataException e) {
            e.getExceptionMsg();
        }
        String[][] array1 = {
                {"4", "10", "15", "25"},
                {"40", "11", "15", "30"},
                {"55", "10", "10", "2"},
                {"0", "10", "11", "22"},
                {"1", "10", "15", "40"}

        };
        try {
            processingArray(array1, 4);
        } catch (MyArraySizeException e) {
            e.getExceptionMsg();
        } catch (MyArrayDataException e) {
            e.getExceptionMsg();
        }
        String[][] array2 = {
                {"4", "10", "15", "25"},
                {"40", "11", "15", "30"},
                {"55", "10", "10", "2"},
                {"0", "Ку-Ку", "11", "22"}

        };

        try {
            processingArray(array2, 4);
        } catch (MyArraySizeException e) {
            e.getExceptionMsg();
        } catch (MyArrayDataException e) {
            e.getExceptionMsg();
        }

    }

    public static void processingArray(String[][] array, int size) throws MyArraySizeException, MyArrayDataException {
        if (array.length != size || array[0].length != size) {
            throw new MyArraySizeException();
        }
        int sum = 0;


        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                try {
                    sum = sum + Integer.parseInt(array[i][j]);
                } catch (Exception e) {
                    throw new MyArrayDataException(i, j);
                }

            }
        }
        System.out.println("Сумма: " + sum);


    }

}
