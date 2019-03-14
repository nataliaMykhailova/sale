package oktenweb.demosale.services;

import oktenweb.demosale.dao.UserDao;
import oktenweb.demosale.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByEmailIgnoreCase(username);
        if (user==null){
            throw new UsernameNotFoundException(String.format("No user found with username:", username));
        }
        return JwtUserFactory.create(user);
    }
}
