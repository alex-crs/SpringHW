package Lesson2;

import Lesson2.polyclinic.Room;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Registry {

    void whatsWrongWithYou(Human human) {
        System.out.println("Что с Вами случилось?");
        human.answerWhatsWrong();
    }

    void goTo(Human human) {
        ApplicationContext registryContext = new AnnotationConfigApplicationContext(DoctorConfig.class);
        Room room = registryContext.getBean(human.getDisease().toString(), Room.class);
        System.out.println(String.format("В кабинете № %s, Вас ожидает врач %s", room.getRoomNumber(), room.getDoctor()));
        System.out.println("Пациент зашел в кабинет №" + room.getRoomNumber() + "\n" + "Врач с порога ему говорит:");
        room.heal();
    }
}
