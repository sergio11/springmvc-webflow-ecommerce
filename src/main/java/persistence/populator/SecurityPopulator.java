package persistence.populator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import persistence.models.Address;
import persistence.models.Authority;
import persistence.models.AuthorityEnum;
import persistence.models.Gender;
import persistence.models.User;
import persistence.repositories.AuthorityRepository;
import persistence.repositories.CountryRepository;

/**
 * @author sergio
 */
@Component
@Profile("development")
public class SecurityPopulator implements Serializable {
    
    private static Logger logger = LoggerFactory.getLogger(SecurityPopulator.class);

    @Autowired
    private AuthorityRepository autorityRepository;
    @Autowired
    private CountryRepository countryRepository;
    
    @Order(2)
    @EventListener(ContextRefreshedEvent.class)
    @Transactional
    public void contextRefreshedEvent() {
        logger.info("SETUP SECURITY INIT DATA");

        List<User> users = new ArrayList();
        
        // Data for user 1
        User user1 = new User();
        user1.setUsername("sergio11");
        user1.setPasswordClear("bisite00");
        user1.setConfirmPassword("bisite00");
        user1.setPassword("$2a$10$0eCQpFRdw8i6jJzjj/IuNuKpJYnLaO5Yp9xSJ3itcfPmQNXVhmNyu");
        user1.setEmail("sss4esob@gmail.com");
        user1.setFullName("Sergio Sánchez Sánchez");
        user1.setGender(Gender.MAN);
        
        Calendar user1Birthday = GregorianCalendar.getInstance();
        user1Birthday.add(Calendar.DATE, 15);
        user1Birthday.add(Calendar.MONTH, 12);
        user1Birthday.add(Calendar.YEAR, 1992);
        user1.setBirthday(user1Birthday.getTime());
        users.add(user1);
        
        Address address = new Address();
        address.setCity("Salamanca");
        address.setStreet("Avd de los Maristas 12");
        address.setCountry(countryRepository.findByCode("AD"));
        address.setZipCode("05005");
        
        user1.addAddress(address);
        
        // Data for user 2
        User user2 = new User();
        user2.setUsername("dani33");
        user2.setPasswordClear("bisite00");
        user2.setConfirmPassword("bisite00");
        user2.setPassword("$2a$10$0eCQpFRdw8i6jJzjj/IuNuKpJYnLaO5Yp9xSJ3itcfPmQNXVhmNyu");
        user2.setEmail("danimarica@gmail.com");
        user2.setFullName("Daniel Pérez Martín");
        user2.setGender(Gender.MAN);
        
        Calendar user2Birthday = GregorianCalendar.getInstance();
        user2Birthday.add(Calendar.DATE, 12);
        user2Birthday.add(Calendar.MONTH, 2);
        user2Birthday.add(Calendar.YEAR, 1989);
        user2.setBirthday(user2Birthday.getTime());
        
        users.add(user2);
        
        //Data for User 3
        User user3 = new User();
        user3.setUsername("pedro_san");
        user3.setPasswordClear("bisite00");
        user3.setConfirmPassword("bisite00");
        user3.setPassword("$2a$10$0eCQpFRdw8i6jJzjj/IuNuKpJYnLaO5Yp9xSJ3itcfPmQNXVhmNyu");
        user3.setEmail("pedrosanchez@gmail.com");
        user3.setFullName("Pedro Sánchez Martín");
        user3.setGender(Gender.MAN);
        
        Calendar user3Birthday = GregorianCalendar.getInstance();
        user3Birthday.add(Calendar.DATE, 12);
        user3Birthday.add(Calendar.MONTH, 2);
        user3Birthday.add(Calendar.YEAR, 1989);
        user3.setBirthday(user3Birthday.getTime());
        
        
        Set<Authority> authorities = new HashSet();
        
        // Add Authorities
        Authority authorityAdmin = new Authority();
        authorityAdmin.setType(AuthorityEnum.ROLE_ADMIN);
        authorityAdmin.setDescription("Rol de los administradores de la tienda");
        authorityAdmin.addUser(user1);
        authorities.add(authorityAdmin);
        
        Authority authorityCustomer = new Authority();
        authorityCustomer.setType(AuthorityEnum.ROLE_CONSUMER);
        authorityCustomer.setDescription("Rol de los consumidores de la tienda");
        authorityCustomer.addUser(user1);
        authorityCustomer.addUser(user2);
        authorityCustomer.addUser(user3);
        authorities.add(authorityCustomer);
        
        try{
            autorityRepository.deleteAll();
            autorityRepository.save(authorities);
        } catch(Exception e){
            logger.error(e.getMessage());
        }
        
    }
}
