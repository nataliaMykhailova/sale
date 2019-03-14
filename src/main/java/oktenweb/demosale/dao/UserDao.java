package oktenweb.demosale.dao;

import oktenweb.demosale.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {
    User findByEmailIgnoreCase( String username);
}
