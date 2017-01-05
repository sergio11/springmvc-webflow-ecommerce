package persistence.populator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import persistence.models.Authority;
import persistence.models.AuthorityEnum;
import persistence.models.User;
import persistence.repositories.UserRepository;

/**
 *
 * @author sergio
 */
@Component
public class SecurityPopulator implements Serializable {
    
    private static Logger logger = LoggerFactory.getLogger(SecurityPopulator.class);

    @Autowired
    private UserRepository userRepository;
    
    @EventListener(ContextRefreshedEvent.class)
    @Transactional
    public void contextRefreshedEvent() {
        logger.info("SETUP SECURITY INIT DATA");
        userRepository.deleteAll();
        Set<Authority> authorities = new HashSet();
        List<User> users = new ArrayList();
        
        User user1 = new User();
        user1.setUsername("sergio11");
        user1.setPasswordClear("bisite00");
        user1.setConfirmPassword("bisite00");
        user1.setPassword("$2a$10$0eCQpFRdw8i6jJzjj/IuNuKpJYnLaO5Yp9xSJ3itcfPmQNXVhmNyu");
        user1.setEmail("sss4esob@gmail.com");
        user1.setFullName("Sergio Sánchez Sánchez");
        users.add(user1);
        
        Authority authorityAdmin = new Authority();
        authorityAdmin.setType(AuthorityEnum.ROLE_ADMIN);
        authorityAdmin.setDescription("Rol de los administradores de la tienda");
        authorityAdmin.addUser(user1);
        authorities.add(authorityAdmin);
        
        Authority authorityCustomer = new Authority();
        authorityCustomer.setType(AuthorityEnum.ROLE_CONSUMER);
        authorityCustomer.setDescription("Rol de los consumidores de la tienda");
        authorityCustomer.addUser(user1);
        authorities.add(authorityCustomer);
        
        userRepository.save(users);
        
    }
}
