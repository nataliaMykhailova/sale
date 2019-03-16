package oktenweb.demosale.controlers;

import oktenweb.demosale.models.User;
import oktenweb.demosale.services.UserServiсe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class UserControler {
    @Autowired
    private UserServiсe userServiсe;

    @GetMapping(value = "/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity <List<User>> getAllUsers() {
        List<User>users = userServiсe.findAll();
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);

    }
    @GetMapping(value = "/getUser")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<User> getUser(Principal principal){
        User user = userServiсe.getUserByEmail(principal.getName());
        return new ResponseEntity<User>(user, HttpStatus.OK);

    }

}
