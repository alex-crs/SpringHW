package SpringLevel1.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "Product")
@NamedQueries({
        @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p"),
        @NamedQuery(name = "Product.findById", query = "SELECT p FROM Product p WHERE p.id = :id"),
        @NamedQuery(name = "Product.getByTitle", query = "SELECT p FROM Product p WHERE p.title = :title")
})
@NoArgsConstructor
@Data
public class Product {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic
    private int id;

    @NonNull
    @Column(name = "title")
    private String title;

    @NonNull
    @Column(name = "cost")
    private long cost;
}
