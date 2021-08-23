package Lesson2.polyclinic;

import Lesson2.Human;
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
    public void helloDoctor() {
        System.out.println(doctor + ": Я вылечу вашу слепоту!");
    }

    @Override
    public void cure(Human human) {
        System.out.println("<<---Лечит--->>");
        human.setDisease(null);
        System.out.println(doctor + ": Как вы себя чувствуете?");
        human.feelingAnswer();
    }
}
