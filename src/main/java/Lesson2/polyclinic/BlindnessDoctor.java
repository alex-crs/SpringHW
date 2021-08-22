package Lesson2.polyclinic;

import org.springframework.stereotype.Component;

@Component("blindness")
public class BlindnessDoctor implements Room {
    private int roomNumber = 1;
    private String doctor = "Окулист";

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getDoctor() {
        return doctor;
    }

    @Override
    public void heal() {
        System.out.println("Окулист: Я вылечу вашу слепоту!");
    }
}
