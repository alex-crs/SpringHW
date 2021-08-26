package Lesson2;

import Lesson2.illness.Disease;
import Lesson2.polyclinic.Room;
import org.springframework.beans.factory.annotation.Autowired;

public class Patient implements Human {
    @Autowired
    private Disease disease;

    public void setDisease(Disease disease) {
        this.disease = disease;
    }

    @Override
    public void feelingAnswer() {
        if (disease != null) {
            System.out.println("Пациент: Плохо!");
        } else {
            System.out.println("Пациент: Отлично! Я выздоровел!");
        }
    }

    @Override
    public Disease getDisease() {
        return this.disease;
    }

    public void answerWhatsWrong() {
        disease.problem();
    }

    public void walkToDoctor(Room room) {
        System.out.println("Пациент зашел в кабинет №" + room.getRoomNumber() + "\n" + "Врач с порога ему говорит:");
        room.helloDoctor();
    }
}
