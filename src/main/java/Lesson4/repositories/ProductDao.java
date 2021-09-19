package Lesson4.repositories;

import Lesson4.entities.Product;
import org.apache.log4j.Logger;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;
import java.util.List;

@Service("ProductDao")
@Transactional
public class ProductDao {
    Logger logger = Logger.getLogger(ProductDao.class);
    EntityManagerFactory factory = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(Product.class)
            .buildSessionFactory();
    ;
    EntityManager em = factory.createEntityManager();

    public ProductDao() {
    }

    public List<Product> getAll() {
        logger.info("Запрос списка продуктов");
        em.getTransaction().begin();
        List<Product> list = em.createNamedQuery("Product.findAll", Product.class).getResultList();
        em.getTransaction().commit();
        return list;
    }

    public Product getProductById(int id) {
        logger.info("Запрос продукта c ID=" + id);
        Product product = null;
        try {
            product = em.createNamedQuery("Product.findById", Product.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (Exception e) {
            logger.info("Продукт не найден");
        }
        if (product != null) {
            logger.info("Получен положительный ответ: " + product);
        }
        return product;
    }

    public void addProduct(Product product) {
        em.getTransaction().begin();
        em.persist(product);
        em.getTransaction().commit();
    }

    public int deleteProductById(int id) {
        logger.info("Запрос удаления продукта c ID=" + id);
        em.getTransaction().begin();
        Product product = em.find(Product.class, id);
        if (product != null) {
            logger.info("Происходит удаление продукта: " + product);
            em.remove(product);
            em.getTransaction().commit();
            return 1;
        } else {
            return -1;
        }
    }

    public void addOrUpdate(Product product) {
        logger.info("Изменение продукта c ID=" + product.getId() + " " + product.getTitle());
        em.getTransaction().begin();
        Product searchResult = em.createNamedQuery("Product.getByTitle", Product.class)
                .setParameter("title", product.getTitle()).getSingleResult();
        if (searchResult != null) {
            searchResult.setTitle(product.getTitle());
            searchResult.setCost(product.getCost());
        } else {
            em.persist(product);
        }
        em.getTransaction().commit();
    }

}
