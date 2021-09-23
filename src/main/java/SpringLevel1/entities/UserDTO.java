package SpringLevel1.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserDTO {
    int id;
    String username;
    List<String> roleList;

    public UserDTO getFromUser(User user) {
        setId(user.getId());
        setUsername(user.getUsername());
        roleList = user.getRolesList();
        return this;
    }

}
