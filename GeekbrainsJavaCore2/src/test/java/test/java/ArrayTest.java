package test.java;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)

public class ArrayTest {
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new int[]{1, 1, 1, 1}},
                {new int[]{4, 1, 1, 1, 4, 1, 1}},
                {new int[]{4, 4, 4, 4, 4, 4, 4, 4}},
                {new int[]{4, 1, 4, 6, 0, 6}},
                {new int[]{4, 7, 4, 6, 0, 4}},
                {new int[]{4, -3, 0, 1, 0, 6, 3, 5}},
        });
    }

    private int[] array;

    public ArrayTest(int[] array) {

        this.array = array;
    }



    private Calculate calculate;

    @Before
    public void init() {
        calculate = new Calculate();
    }


    @Test(expected = RuntimeException.class)
    public void test1getLastEntryNumber() {
        calculate.getLastEntryNumber(4, array);
    }
    @Test(expected = RuntimeException.class)
    public void test2getNewArray() {
        calculate.getNewArray(array);
    }
    @Test
    public void test3getNewArray() {
        Assert.assertNull(calculate.getNewArray(array));
    }
    @Test
    public void test4getNewArray() {
        int[] arrayTest = {6, 5, 1, 2, 3, 3};
        for(int i = 0; i < arrayTest.length; i++) {
            Assert.assertEquals(arrayTest[i], calculate.getNewArray(array)[i]);
        }
    }
    @Test
    public void test5getNewArray() {
        int[] arrayTest = {6, 0, 6};
        for(int i = 0; i < arrayTest.length; i++) {
            Assert.assertEquals(arrayTest[i], calculate.getNewArray(array)[i]);
        }
    }
    @Test
    public void test6checkArray(){
        Assert.assertTrue(calculate.checkArray(1, 4, array));
    }
}
