package Lesson2.polyclinic;

import org.springframework.stereotype.Component;

@Component("deafness")
public class DeafnessDoctor implements Room {
    private int roomNumber = 2;
    private String doctor = "Отоларинголог";

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getDoctor() {
        return doctor;
    }

    @Override
    public void heal() {
        System.out.println("Отоларинголог: Я вылечу вашу глухоту!");
    }
}
