package Lesson4;

import Lesson4.repositories.ProductBase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Launcher {
    public static ProductBase base = new ProductBase();
    public static void main(String[] args) {
        SpringApplication.run(Launcher.class, args);
    }
}