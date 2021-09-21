package SpringLevel1.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "Users")
public class User {
    @Id
    @NonNull
    @Column(name = "username")
    private String username;

    @NonNull
    @Column(name = "userPass")
    private String password;

    @NonNull
    @Column(name = "enabled")
    private int enabled;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "username"),
            inverseJoinColumns = @JoinColumn(name = "authority"))
    private Collection<Role> roles;
}
