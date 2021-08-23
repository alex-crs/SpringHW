package Lesson2;

import Lesson2.polyclinic.Room;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Registry {

    void howAreYouFeeling(Human human){
        System.out.println("Регистратура: Как вы себя чувствуете?");
        human.feelingAnswer();
    }

    void whatsWrongWithYou(Human human) {
        System.out.println("Регистратура: Что с Вами случилось?");
        human.answerWhatsWrong();
    }

    Room lookingForADoctor(Human human) {
        ApplicationContext registryContext = new AnnotationConfigApplicationContext(DoctorConfig.class);
        Room room = registryContext.getBean(human.getDisease().toString(), Room.class);
        System.out.println(String.format("Регистратура: В кабинете № %s, Вас ожидает врач %s", room.getRoomNumber(), room.getDoctor()));
        return room;
    }
}
