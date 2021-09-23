package SpringLevel1.repositories;

import SpringLevel1.entities.Role;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {
//    Optional<Role> findById(String id);
}
