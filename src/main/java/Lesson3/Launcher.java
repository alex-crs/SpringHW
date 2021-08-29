package Lesson3;

import Lesson3.entites.Product;
import Lesson3.repositories.ProductBase;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import java.net.URL;
import java.security.ProtectionDomain;

public class Launcher {
    public static ProductBase base = new ProductBase();
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        ProtectionDomain domain = Launcher.class.getProtectionDomain();
        URL location = domain.getCodeSource().getLocation();
        productBaseStartFill(base);
        System.out.println(base.toString());
        WebAppContext webapp = new WebAppContext();
        webapp.setContextPath("/");
        webapp.setWar(location.toExternalForm());
        server.setHandler(webapp);
        server.start();
        server.join();
    }

    static void productBaseStartFill(ProductBase base) {
        base.addToBase(new Product(203,"Sofa", 12000));
        base.addToBase(new Product(805,"Chair", 1800));
        base.addToBase(new Product(152,"Bed", 35000));
        base.addToBase(new Product(84,"Lamp", 11000));
    }
}