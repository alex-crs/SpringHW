package Lesson4.entities;

import javax.persistence.*;

@Entity
@Table(name = "Product")
@NamedQueries({
        @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p"),
        @NamedQuery(name = "Product.findById", query = "SELECT p FROM Product p WHERE p.id = :id"),
        @NamedQuery(name = "Product.getByTitle", query = "SELECT p FROM Product p WHERE p.title = :title")
})
public class Product {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "cost")
    private long cost;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public Product() {
    }

    public Product(int id, String title, long cost) {
        this.id = id;
        this.title = title;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return String.format("Product id=[%s], title = [%s], cost = [%s]", id, title, cost);
    }
}
