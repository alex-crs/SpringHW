package Lesson2;

import Lesson2.illness.Blindness;
import Lesson2.illness.Deafness;
import Lesson2.illness.Disease;
import Lesson2.illness.Trauma;
import Lesson2.polyclinic.BlindnessDoctor;
import Lesson2.polyclinic.DeafnessDoctor;
import Lesson2.polyclinic.Room;
import Lesson2.polyclinic.TraumaDoctor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan("Lesson2.polyclinic")
public class DoctorConfig {

//    @Bean(name = "blindness")
//    @Scope("prototype")
//    public Room room(Blindness blindness){
//        Room room = new BlindnessDoctor();
//        return room;
//    }

//    @Bean(name = "blindness")
//    @Scope("prototype")
//    public Room room(Deafness deafness){
//        Room room = new DeafnessDoctor();
//        return room;
//    }

//    @Bean(name = "trauma")
//    @Scope("prototype")
//    public Room room(Trauma trauma){
//        Room room = new TraumaDoctor();
//        return room;
//    }

}
