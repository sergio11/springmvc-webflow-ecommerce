package persistence.populator;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import persistence.models.ConsumerTypeEnum;
import persistence.models.Product;
import persistence.models.ProductLine;
import persistence.models.ProductStatusEnum;
import persistence.models.Review;
import persistence.models.ReviewStatusEnum;
import persistence.models.User;
import persistence.repositories.ProductRepository;
import persistence.repositories.UserRepository;

/**
 * @author sergio
 */
@Component
@Profile("development")
public class ProductPopulator implements Serializable {
    
    private static Logger logger = LoggerFactory.getLogger(ProductPopulator.class);

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    
    @Order(2)
    @EventListener(ContextRefreshedEvent.class)
    @Transactional
    public void contextRefreshedEvent() {
        logger.info("SETUP PRODUCT INIT DATA");
        
        productRepository.deleteAll();
        
        Product product = new Product();
        product.setName("Nike Air Max 2017");
        product.setConsumerType(ConsumerTypeEnum.MAN);
        product.setPrice(190.0);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 3);
        product.setAvailableFrom(c.getTime());
        product.setShortDescription("Las zapatillas de running Nike Air Max 2017 para hombre, diseñadas a la perfección con la sujeción y la transpirabilidad .");
        product.setDescription("Las zapatillas de running Nike Air Max 2017 para hombre, diseñadas a la perfección con la sujeción y la transpirabilidad adecuadas donde más lo necesitas, cuentan con una parte superior Flymesh que se combina con la suave amortiguación de la unidad Max Air de longitud completa.");
        product.setStatus(ProductStatusEnum.PUBLISHED);
        
        Review review = new Review();
        review.setProduct(product);
        review.setRating(1.0f);
        review.setBody("I would never recommend these to anyone, where i have always been happy with nike shoes the quality of these was so bad.");
        review.setStatus(ReviewStatusEnum.PENDING);
        User user = userRepository.findByUsername("sergio11");
        review.setUser(user);
        
        ProductLine line = new ProductLine();
        line.setDescription("Máximo Negro");
        line.setImage("test1.jpg");
        line.setProduct(product);
        line.setStock(5);
        
        ProductLine line2 = new ProductLine();
        line2.setDescription("Extraordinario Naranja");
        line2.setImage("test2.jpg");
        line2.setProduct(product);
        line2.setStock(4);
        
        ProductLine line3 = new ProductLine();
        line3.setDescription("Rojo Gimnasio");
        line3.setImage("test3.jpg");
        line3.setProduct(product);
        line3.setStock(0);
        
        productRepository.save(product);
        
    }
}
