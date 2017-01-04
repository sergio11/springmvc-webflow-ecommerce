package web.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.models.User;
import persistence.repositories.UserRepository;
import web.services.UserService;

/**
 *
 * @author sergio
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void updatePassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPasswordClear()));
        userRepository.save(user);
    }
    
}
