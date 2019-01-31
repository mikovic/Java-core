package ru.geekbrains.classes;

import ru.geekbrains.classes.animals.Cat;
import ru.geekbrains.classes.animals.Dog;
import ru.geekbrains.classes.obstacles.Cross;
import ru.geekbrains.classes.obstacles.Obstacle;
import ru.geekbrains.classes.obstacles.Wall;
import ru.geekbrains.classes.obstacles.Water;

public class Application {

    public static void main(String[] args) {
        Team team = new Team("Barcelona");
        Course c = new Course();
        c.doIt(team);
        team.showResults();
        Participant[] participant = new Participant[]{new Dog("Стрелка", 5, 10, 5), new Dog("Белка", 15, 6, 10), new Cat("Мурзик", 9, 14, 0), new Robot("Роботкоп", 50, 50, 50)};
        Team team1 = new Team("Другая команда", participant);
        c.doIt(team1);
        team1.showResults();


    }
}
