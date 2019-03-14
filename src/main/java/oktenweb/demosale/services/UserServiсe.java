package oktenweb.demosale.services;

import oktenweb.demosale.models.User;

import java.util.List;

public interface UserServiсe {
    User save(User user);

    List<User> findAll();
}
