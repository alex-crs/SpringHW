package Lesson4;

import Lesson4.entites.Product;
import Lesson4.repositories.ProductBase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Launcher {
    public static ProductBase base = new ProductBase();
    public static void main(String[] args) {
        SpringApplication.run(Launcher.class, args);
        productBaseStartFill(base);

    }

    static void productBaseStartFill(ProductBase base) {
        base.addToBase(new Product(203,"Sofa", 12000));
        base.addToBase(new Product(805,"Chair", 1800));
        base.addToBase(new Product(152,"Bed", 35000));
        base.addToBase(new Product(84,"Lamp", 11000));
    }

}