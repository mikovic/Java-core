package ru.geekbrains.classes.lesson7_Class_Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) throws Exception {
       start(Calculator.class);
    }
    public static void start(Class<?> className) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        Class myCal = className;
        Method[] methods = myCal.getDeclaredMethods();
        ArrayList<Method> list = new ArrayList<>();
        Method beforeSuite = null;
        Method afterSuite = null;
        myCal.getAnnotations();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Test.class)) {
                list.add(method);
            }
            if (method.isAnnotationPresent(BeforeSuite.class)) {
                if (beforeSuite == null) beforeSuite = method;
                else throw new RuntimeException("Более одного метода с аннотацией BeforeSuite");
            }
            if (method.isAnnotationPresent(AfterSuite.class)) {
                if (afterSuite == null) afterSuite = method;
                else throw new RuntimeException("Более одного метода с аннотацией AfterSuite");
            }
        }
        list.sort(new MyComparator());
        Object calculator = myCal.newInstance();
        beforeSuite.invoke(calculator, null);
        for (Method method : list) {
            int params = method.getParameterCount();
            if (params == 1) {
                method.invoke(calculator, 25);
            }
            if (params == 2) {
                method.invoke(calculator, 15, 10);
            }
        }
        afterSuite.invoke(calculator, null);
    }
}


