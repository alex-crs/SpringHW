package Lesson2;

import Lesson2.illness.Disease;
import org.springframework.beans.factory.annotation.Autowired;

public class Patient implements Human {
    @Autowired
    private Disease disease;

    public void setDisease(Disease disease) {
        this.disease = disease;
    }

    @Override
    public Disease getDisease() {
        return this.disease;
    }

    public void answerWhatsWrong() {
        disease.problem();
    }
}
