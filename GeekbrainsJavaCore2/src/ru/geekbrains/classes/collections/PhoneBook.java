package ru.geekbrains.classes.collections;

import java.util.HashSet;

public class PhoneBook {
    private HashSet<Contact> set;
    public PhoneBook(HashSet<Contact> set){
        this.set = set;
    }
    public PhoneBook(){
        this.set = new HashSet<Contact>();
    }

    public HashSet<Contact> getSet() {
        return set;
    }
    public void addContact(Contact contact){
       this.set.add(contact);
    }
    public void showAllContacts(){
        for (Contact c : this.set){
            System.out.println(c.getName()+".  Телефоны абонента:");
            for (String phoneNumber : c.getList()){
                System.out.print(phoneNumber+"  ");

            }
            System.out.println();
        }
    }
    public void getContact(String name) {
        boolean flag = false;
        for (Contact c : this.set){
            if(c.getName().equals(name)){
                System.out.println(c.getName()+".  Телефоны абонента:");
                for (String phoneNumber : c.getList()){
                    System.out.println(phoneNumber);
                }

                flag = true;
            }

        }
        if (!flag) {
            System.out.println("Абонент с именем "+name+" не найден!!!!");
        }
    }

}
