package SpringLevel1.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "authorities")
public class Role {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic
    private int id;

    @Column(name = "authority")
    private String authority;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "Users_authorities",
            joinColumns = @JoinColumn(name = "authorities_id"),
            inverseJoinColumns = @JoinColumn(name = "users_id"))
    private Collection<User> users;
}
