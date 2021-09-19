package SpringLevel1.repositories;

import SpringLevel1.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
    List<Product> findAll();

    Optional<Product> findById(int id);

    int deleteById(int id);

    Page<Product> findByCostBetween(long minCost, long maxCost, Pageable pageable);

    Product save(Product product);
}
