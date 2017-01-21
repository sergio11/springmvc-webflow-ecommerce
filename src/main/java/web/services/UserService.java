package web.services;

import java.util.Set;
import org.springframework.web.multipart.MultipartFile;
import persistence.models.Address;
import persistence.models.User;

/**
 * @author sergio
 */
public interface UserService {
    void updatePassword(User user);
    void create(User user);
    void create(User user, MultipartFile avatarFile);
    boolean hasAddresses(String username);
    Set<Address> getAddresses(String username);
}
