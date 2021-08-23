package Lesson2.polyclinic;

import Lesson2.Human;
import org.springframework.stereotype.Component;

@Component("trauma")
public class TraumaDoctor implements Room {
    private int roomNumber = 3;
    private String doctor = "Травматолог";

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getDoctor() {
        return doctor;
    }

    @Override
    public void helloDoctor() {
        System.out.println(doctor + ": Я вылечу ваш перелом!");
    }

    @Override
    public void cure(Human human) {
        System.out.println("<<---Лечит--->>");
        human.setDisease(null);
        System.out.println(doctor + ": Как вы себя чувствуете?");
        human.feelingAnswer();
    }
}
