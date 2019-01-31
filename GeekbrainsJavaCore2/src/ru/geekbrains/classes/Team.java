package ru.geekbrains.classes;

import ru.geekbrains.classes.animals.Cat;
import ru.geekbrains.classes.animals.Dog;

public class Team {

    private String name;
    Participant[] participants;

    public Team(String name) {
        this.name = name;

        this.participants = new Participant[]{new Cat("Барсик", 10, 12, 0), new Dog("Дружок", 20, 5, 15), new Cat("Мурзик", 9, 14, 0), new Robot("Вертер", 50, 50, 50)};
    }

    public Team(String name, Participant[] participants) {
        this.name = name;
        this.participants = participants;
    }

    public String getName() {
        return this.name;
    }

    public void showResults() {

        System.out.println("\n" + "Итоги команды '" + getName() + "':");
        for (Participant participant : participants) {
            System.out.println(participant);
        }
        System.out.println("--------------------");
    }

    public Participant[] getParticipants() {
        return participants;

    }

}
