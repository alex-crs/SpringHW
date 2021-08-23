package Lesson2;

import Lesson2.polyclinic.Room;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainClass {
    public static void main(String[] args) {
        ApplicationContext patientContext = new AnnotationConfigApplicationContext(PatientConfig.class);
        Human human = patientContext.getBean("patient",Human.class);
        Registry registry = new Registry();
        registry.howAreYouFeeling(human);
        registry.whatsWrongWithYou(human);
        Room room = registry.lookingForADoctor(human);
        human.walkToDoctor(room);
        room.cure(human);
    }
}
