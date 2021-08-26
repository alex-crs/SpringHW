package Lesson2;

import Lesson2.illness.Disease;
import Lesson2.polyclinic.Room;

public interface Human {
    void answerWhatsWrong();
    void setDisease(Disease disease);
    Disease getDisease();
    void walkToDoctor(Room room);
    void feelingAnswer();
}
