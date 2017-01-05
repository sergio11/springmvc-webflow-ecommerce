package web.services.impl;

import java.io.IOException;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import persistence.models.Authority;
import persistence.models.AuthorityEnum;
import persistence.models.User;
import persistence.repositories.AuthorityRepository;
import persistence.repositories.UserRepository;
import web.models.upload.RequestUploadAvatarFile;
import web.services.UserService;
import web.uploads.UploadAvatarStrategy;

/**
 *
 * @author sergio
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    
    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthorityRepository auhtorityRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UploadAvatarStrategy uploadAvatarStrategy;

    @Override
    public void updatePassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPasswordClear()));
        userRepository.save(user);
    }

    @Override
    public void create(User user) {
        Authority authority = auhtorityRepository.findByType(AuthorityEnum.ROLE_CONSUMER);
        if(authority != null){
            logger.info(authority.toString());
            user.addAuthority(authority);
            user.setPassword(passwordEncoder.encode(user.getPasswordClear()));
        }
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
}
