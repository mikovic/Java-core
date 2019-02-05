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
        Contact contact1 = new Contact("Миша");
        contact1.addPhone("2-68-98");
        contact1.addPhone("24-56-89");
        contact1.addPhone("147-54-89");
        Contact contact2 = new Contact("Маша");
        contact2.addPhone("234-45-67");
        contact2.addPhone("147-56-30");
        Contact contact3 = new Contact("Миша");
        contact3.addPhone("234-11-11");
        PhoneBook phoneBook =new PhoneBook();
        phoneBook.addContact(contact1);
        phoneBook.addContact(contact2);
        phoneBook.addContact(contact3);
        phoneBook.showAllContacts();
        phoneBook.getContact("Петя");
        phoneBook.getContact("Миша");
        phoneBook.getContact("Маша");
        phoneBook.getContact("Люба");


    }




}
