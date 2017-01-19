package persistence.repositories;

import java.util.Date;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import persistence.models.User;

/**
 * @author sergio
 */
@Transactional
public interface UserRepository extends DataTablesRepository<User, Long> {
    User findByUsername(String username);
    @Query("SELECT COUNT(u.id) FROM User u WHERE u.username=:username")
    Long existsUserWithUsername(@Param("username") String username);
    @Query("SELECT COUNT(u.id) FROM User u WHERE u.email = :email")
    Long existsUserWithEmail(@Param("email") String email);
    @Modifying(clearAutomatically = true)
    @Query("update User user set user.lastLoginAccess =:lastLoginAccess where user.username =:username")
    void updateLastLoginAccess(@Param("username") String username, @Param("lastLoginAccess") Date lastLoginAccess);
    @Query("SELECT COUNT(u.addresses) FROM User u WHERE u.username = :username")
    Long countAddresses(@Param("username") String username);
}
