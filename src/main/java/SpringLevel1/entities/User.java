package SpringLevel1.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "Users")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic
    private int id;

    @Column(name = "username")
    private String username;

    @NonNull
    @Column(name = "password")
    private String password;

    @NonNull
    @Column(name = "enabled")
    private int enabled;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "Users_authorities",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "authorities_id"))
    private Collection<Role> roles;

    public List<String> getRolesList() {
        if (roles != null) {
            List<String> list = new ArrayList<>();
            list = new ArrayList<>();
            for (Role r : roles) {
                list.add(r.getAuthority().replaceAll("ROLE_", ""));
            }
            return list;
        } else {
            return null;
        }
    }

    public int checkExistRole(String role){
        if (roles!=null){
            for (Role r:roles){
                if (r.getAuthority().equals(role)){
                    return -1;
                }
            }
        }
        return 1;
    }
}
