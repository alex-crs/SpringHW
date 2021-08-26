package Lesson2;

import Lesson2.illness.Blindness;
import Lesson2.illness.Deafness;
import Lesson2.illness.Disease;
import Lesson2.illness.Trauma;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan("Lesson2")
public class PatientConfig {
    @Bean("disease")
    public Disease disease() {
        return new Trauma();
    }

    @Bean(name = "patient")
    @Scope("prototype")
    public Human human(Disease disease){
        Human human = new Patient();
        human.setDisease(disease);
        return human;
    }

}
