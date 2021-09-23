package SpringLevel1.controllers;

import SpringLevel1.entities.Role;
import SpringLevel1.entities.User;
import SpringLevel1.entities.UserDTO;
import SpringLevel1.repositories.UserService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    @Resource(name = "UserService")
    private UserService userService;

    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/userList", method = RequestMethod.GET)
    public String getRole(@ModelAttribute("newUser") User newUser, Model model) {
        User user = new User();
        model.addAttribute("userList", listBuilder());
        model.addAttribute("newUser", user);
        return "user-list";
    }

    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String addUser(Model model, @ModelAttribute("newUser") User user) {
        user.setEnabled(1);
        user.setPassword("{noop}" + user.getPassword());
        userService.addUser(user);
        model.addAttribute("userList", listBuilder());
        return "user-list";
    }

    @Secured({"ROLE_ADMIN"})
    @RequestMapping("/deleteUser")
    public String deleteUser(Model model, @ModelAttribute("newUser") User user, @RequestParam("id") int id) {
        userService.deleteUserById(id);
        model.addAttribute("userList", listBuilder());
        return "user-list";
    }

    @Secured({"ROLE_ADMIN"})
    @RequestMapping("/clearRoles")
    public String changeRole(@ModelAttribute("newUser") User user, @RequestParam("id") int id, Model model) {
        userService.clearAllPermissions(id);
        model.addAttribute("userList", listBuilder());
        return "user-list";
    }

    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/addRole", method = RequestMethod.POST)
    public String addRole(@ModelAttribute("newUser") User user, @RequestParam("UserRoles") String permissions, @RequestParam("id") int id,
                          Model model) {
        if (permissions != null && !permissions.equals("")) {
            userService.addPermissions(id, permissions);
        }
        model.addAttribute("userList", listBuilder());
        return "user-list";
    }

    @Secured({"ROLE_ADMIN"})
    private List<UserDTO> listBuilder() {
        List<User> list = userService.findAll();
        List<UserDTO> listDTO = new ArrayList<>();
        for (User l : list) {
            listDTO.add(new UserDTO().getFromUser(l));
        }
        return listDTO;
    }
}
