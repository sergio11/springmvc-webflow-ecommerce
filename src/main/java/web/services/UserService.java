package web.services;

import persistence.models.User;

/**
 * @author sergio
 */
public interface UserService {
    void updatePassword(User user);
}
