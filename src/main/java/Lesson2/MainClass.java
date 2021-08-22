package Lesson2;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainClass {
    public static void main(String[] args) {
        ApplicationContext patientContext = new AnnotationConfigApplicationContext(PatientConfig.class);
        Human human = patientContext.getBean("patient",Human.class);
        Registry registry = new Registry();
        registry.whatsWrongWithYou(human);
        registry.goTo(human);
    }
}
