package Lesson2.polyclinic;

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
    public void heal() {
        System.out.println("Травматолог: Я вылечу ваш перелом!");
    }
}
