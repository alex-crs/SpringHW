package Lesson2.polyclinic;

import Lesson2.Human;

public interface Room {
    void helloDoctor();
    int getRoomNumber();
    String getDoctor();
    void cure(Human human);
}
