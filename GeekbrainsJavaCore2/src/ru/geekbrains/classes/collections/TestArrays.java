package ru.geekbrains.classes.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

public class TestArrays {
    public static void main(String[] args) {
        String[] array = {
                "лето", "зима", "рыбалка", "ветер", "зима", "Kommersant", "footbol", "lenta", "лето", "Kommersant", "lenta", "лето"
        };
        ArrayList<String> list;
        HashSet<String> set;
        list = new ArrayList<String>(Arrays.asList(array));
        set = new HashSet<String>(list);
        for(String str : set) {
            int n =0;
            for (String st : list){
                if(str.equals(st)){
                    n++;
                }
            }
            System.out.println(str+", количество повторений: "+n);

        }



    }




}
