/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence.repositories;

import java.util.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import persistence.models.User;

/**
 * @author sergio
 */
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    @Query("SELECT COUNT(u.id) FROM User u WHERE u.email=:email OR u.username=:username")
    Long existsUserWithEmailOrUsername(@Param("email") String email, @Param("username") String username);
    @Query("SELECT COUNT(u.id) FROM User u WHERE u.id!=:id AND (u.email=:email OR u.username=:username)")
    Long existsUserWithEmailOrUsernameAndNotId(@Param("email") String email, @Param("username") String username, @Param("id") Long id);
    @Modifying(clearAutomatically = true)
    @Query("update User user set user.lastLoginAccess =:lastLoginAccess where user.username =:username")
    void updateLastLoginAccess(@Param("username") String username, @Param("lastLoginAccess") Date lastLoginAccess);
}
