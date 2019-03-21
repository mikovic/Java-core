package test.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static junit.framework.TestCase.fail;

public class Calculate {
    public int getLastEntryNumber(int num, int[] array) throws RuntimeException {
        int pos = -1;

        for (int i = 0; i < array.length; i++) {
            if (array[i] == num) {
                pos = i;
            }
        }
        if (pos < 0) {
            throw new RuntimeException();
        }
        return pos;
    }

    public int[] getNewArray(int[] array) throws RuntimeException {
        int[] newArray = null;
        try {
            int pos = getLastEntryNumber(4, array);
            if (pos != (array.length - 1)) {
                int len = array.length - 1 - pos;
                newArray = new int[len];
                System.arraycopy(array, pos + 1, newArray, 0, len);

            }
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }

        return newArray;
    }

    public boolean checkArray(int num1, int num2, int[] array) {
        boolean flag = false;
        List<Integer> list = IntStream.of(array).boxed().collect(Collectors.toList());
        if (list.contains(num1) && list.contains(num2)) {
            list.removeIf(i -> (i == num1 || i == num2));
            if (list.isEmpty()) {
                flag = true;
            }
        }
        return flag;
    }
}
