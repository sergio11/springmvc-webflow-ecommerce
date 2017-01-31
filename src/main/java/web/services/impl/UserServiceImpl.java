package web.services.impl;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import persistence.models.Address;
import persistence.models.User;
import persistence.repositories.OrderRepository;
import persistence.repositories.UserRepository;
import web.events.user.ChangePasswordEvent;
import web.models.upload.RequestUploadAvatarFile;
import web.services.UserService;
import web.uploads.UploadStrategy;

/**
 * @author sergio
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
    
    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UploadStrategy<Long, RequestUploadAvatarFile> uploadAvatarStrategy;
    @Autowired
    private ApplicationEventPublisher publisher;

    @Override
    public void updatePassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPasswordClear()));
        userRepository.save(user);
        // publish event for another components
        publisher.publishEvent(new ChangePasswordEvent(user.getUsername())); 
    }

    @Override
    public void create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPasswordClear()));
        userRepository.save(user);
    }

    @Override
    public void create(User user, MultipartFile avatarFile) {
        // create user
        this.create(user);
        try {
            RequestUploadAvatarFile uploadAvatar = new RequestUploadAvatarFile(user, avatarFile.getSize(),
                    avatarFile.getBytes(), avatarFile.getContentType(), avatarFile.getOriginalFilename());
            logger.info(uploadAvatar.toString());
            uploadAvatarStrategy.save(uploadAvatar);
        } catch (IOException ex) {
            logger.error(ex.toString());
        }
    }

    @Override
    public boolean hasAddresses(String username) {
        return userRepository.countAddresses(username) > 0;
    }

    @Override
    public Set<Address> getAddresses(String username) {
        User user = userRepository.findByUsername(username);
        logger.info("Address count: " +  user.getAddresses().size());
        return user.getAddresses();
    }

    @Override
    public Long getTotalPurchases(Long id) {
        return orderRepository.countByCustomerId(id);
    }

    @Override
    public Double getTotalSpent(Long id) {
        return orderRepository.getTotalSpentByUser(id);
    }

    @Override
    public Double getTotalSpentThisMonth(Long id) {
        Calendar c = GregorianCalendar.getInstance();
        return orderRepository.getTotalSpentByUserAndMonth(id, c.get(Calendar.MONTH) + 1);
    }
}
