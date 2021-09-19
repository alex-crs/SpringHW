package Lesson4.repositories;

import Lesson4.entities.Product;
import org.apache.log4j.Logger;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.sql2o.Sql2o;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;
import java.io.*;

import org.sql2o.Connection;

import java.util.List;
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

    public List<Product> getAll(int page, int size, Sort.Direction sort) { //возвращает ограниченный список продуктов
        logger.info("Запрос списка продуктов");

        Page<Product> list = productRepository.findAll(PageRequest.of(page, size, Sort.by(sort, "cost")));
        return list.stream().collect(Collectors.toList());
    }

    public Product getProductById(int id) {
        logger.info("Запрос продукта c ID=" + id);
        Product product = null;
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

    public List<Product> findMax() {
        Page<Product> list = productRepository.findAll(PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "cost")));
        for (Product l : list) {
            System.out.println(l);
        }
        return list.stream().collect(Collectors.toList());
    }

    public List<Product> findMin() {
        Page<Product> list = productRepository.findAll(PageRequest.of(0, 1, Sort.by(Sort.Direction.ASC, "cost")));
        for (Product l : list) {
            System.out.println(l);
        }
        return list.stream().collect(Collectors.toList());
    }

    public List<Product> findCostBetween(long minCost, long maxCost, Sort.Direction sort, int page) {
        PageRequest pageRequest = null;
        List<Product> list = productRepository.findByCostBetween(minCost, maxCost, PageRequest.of(page, 5, Sort.by(sort, "cost")));
        return list;
    }

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
