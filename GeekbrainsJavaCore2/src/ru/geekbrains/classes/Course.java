package ru.geekbrains.classes;

import ru.geekbrains.classes.obstacles.Cross;
import ru.geekbrains.classes.obstacles.Obstacle;
import ru.geekbrains.classes.obstacles.Wall;
import ru.geekbrains.classes.obstacles.Water;

public class Course {
    private Obstacle[] obstacles;

    public Course() {
        this.obstacles = new Obstacle[]{new Cross(5), new Wall(3), new Water(7)};

    }
    public void doIt(Team team) {
        for (Participant participant : team.getParticipants()) {
            for (Obstacle obstacle : obstacles) {
                obstacle.doIt(participant);
                if (!participant.isOnDistance()) {
                    break;
                }
            }
        }
    }

}
