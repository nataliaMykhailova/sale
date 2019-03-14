package oktenweb.demosale.services.implement;

import oktenweb.demosale.components.PasswordUtil;
import oktenweb.demosale.dao.UserDao;
import oktenweb.demosale.models.User;
import oktenweb.demosale.services.UserServiсe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserServiceImp implements UserServiсe {
    @Autowired
    private UserDao userDao;

    @Override
    public User save(User user) {
        String password = PasswordUtil.getPasswordHash(user.getPassword());
        user.setPassword(password);
        user.setCreationDate(new Date());
        userDao.save(user);
        return null;
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }
}
