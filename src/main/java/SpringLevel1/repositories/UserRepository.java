package SpringLevel1.repositories;

import SpringLevel1.entities.Product;
import SpringLevel1.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    Optional<User> findOneByUsername(String username);
    Optional<User> findById(int id);
    List<User> findAll();
    void deleteById(int id);
}
