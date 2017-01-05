package web.services;

import org.springframework.web.multipart.MultipartFile;
import persistence.models.User;

/**
 * @author sergio
 */
public interface UserService {
    void updatePassword(User user);
    void create(User user);
    void create(User user, MultipartFile avatarFile);
}
