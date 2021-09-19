package Lesson4.repositories;

import Lesson4.entities.Product;
import org.apache.log4j.Logger;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.sql2o.Sql2o;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.io.*;

import org.sql2o.Connection;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("ProductDao")
@Transactional
public class ProductDao {
    Logger logger = Logger.getLogger(ProductDao.class);
    EntityManagerFactory factory = new Configuration() //удалить
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(Product.class)
            .buildSessionFactory();
    ;
    EntityManager em = factory.createEntityManager(); //удалить

    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDao() {
    }

    public Optional<Product> getProductById(int id) {
        logger.info("Запрос продукта c ID=" + id);
        Optional<Product> product = null;
        try {
            product = productRepository.findById(id);
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

    public int deleteById(int id){
        return productRepository.deleteById(id);
    }

    public int addOrUpdate(Product product) {
        logger.info("Изменение продукта c ID=" + product.getId() + " " + product.getTitle());
        Product searchResult = null;
        em.getTransaction().begin();
        try {
            searchResult = em.createNamedQuery("Product.getByTitle", Product.class)
                    .setParameter("title", product.getTitle()).getSingleResult();
        } catch (NoResultException e) {
            logger.info("Продукт не обнаружен");
        }
        if (searchResult != null) {
            searchResult.setTitle(product.getTitle());
            searchResult.setCost(product.getCost());
            em.getTransaction().commit();
            return 0;
        } else {
            em.persist(product);
        }
        em.getTransaction().commit();
        return 1;
    }

    public Page<Product> findCostBetween(long minCost, long maxCost, Sort.Direction sort, int page) {
        Page<Product> pages = productRepository.findByCostBetween(minCost, maxCost, PageRequest.of(page, 10, Sort.by(sort, "cost")));
        return pages;
    }

    //загружает содержимое текстового файла в базу
    public int loadDataFromFile() {
        File file = new File("update.txt");
        Sql2o sql2o = new Sql2o("jdbc:sqlite:base.db", null, null);
        logger.info(String.format("Запущен метод добавления данных из файла [%s] в базу данных.", file.getName()));
        String insertQuery = "INSERT INTO score(id, score) VALUES (:idParam, :scoreParam)";
        int stringCount = 0;
        try (FileInputStream fis = new FileInputStream(file);
             BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
             Connection sqlCon = sql2o.open()) {
            //Читаем заголовок документа
            String[] head = reader.readLine().split(" ");
            stringCount++;
            if ("id".equals(head[0]) && "score".equals(head[1]) && head.length == 2) {
                while (reader.ready()) {
                    String[] line = reader.readLine().split(" ");
                    sqlCon.createQuery(insertQuery)
                            .addParameter("idParam", line[0])
                            .addParameter("scoreParam", line[1])
                            .executeUpdate();
                    stringCount++;
                }
                logger.info(String.format("Успешно! Прочитано %s строк.", stringCount));
            } else {
                System.out.println("Неверный формат данных.");
            }
        } catch (FileNotFoundException e) {
            logger.error(String.format("Файл [%s] не найден!", file.getName()));
            return -1;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        return stringCount;
    }
}
