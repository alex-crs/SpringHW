package SpringLevel1.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@Table(name = "authorities")
public class Role {
    @Id
    @Column(name = "authority")
    private String authority;
}
