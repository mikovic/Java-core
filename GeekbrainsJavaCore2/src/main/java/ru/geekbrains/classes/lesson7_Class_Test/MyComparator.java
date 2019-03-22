package ru.geekbrains.classes.lesson7_Class_Test;

import java.lang.reflect.Method;
import java.util.Comparator;

public class MyComparator implements Comparator<Method> {
    @Override
    public int compare(Method o1, Method o2) {
        return o1.getAnnotation(Test.class).priority() - o2.getAnnotation(Test.class).priority();
    }
}
