package persistence.populator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import persistence.models.ProductCategory;
import persistence.repositories.ProductCategoryRepository;


/**
 * @author sergio
 */
@Component
@Profile("development")
public class ProductCategoryPopulator implements Serializable {
    
    private static Logger logger = LoggerFactory.getLogger(ProductCategoryPopulator.class);
    
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    
    @Order(3)
    @EventListener(ContextRefreshedEvent.class)
    @Transactional
    public void contextRefreshedEvent() {
        logger.info("SETUP PRODUCT CATEGORIES INIT DATA");
       
        List<ProductCategory> productsCategory = new ArrayList();
        
        ProductCategory sneakers = new ProductCategory();
        sneakers.setName("Zapatillas");
        sneakers.setSlug("zapatillas");
        productsCategory.add(sneakers);
        
        ProductCategory lifeStyle = new ProductCategory();
        lifeStyle.setName("LifeStyle");
        lifeStyle.setSlug("lifestyle");
        lifeStyle.setParent(sneakers);
        productsCategory.add(lifeStyle);
        
        ProductCategory running = new ProductCategory();
        running.setName("Running");
        running.setSlug("running");
        running.setParent(sneakers);
        productsCategory.add(running);
        
        ProductCategory football = new ProductCategory();
        football.setName("Football");
        football.setSlug("football");
        football.setParent(sneakers);
        productsCategory.add(football);
        
        ProductCategory basketball = new ProductCategory();
        basketball.setName("Basketball");
        basketball.setSlug("basketball");
        basketball.setParent(sneakers);
        productsCategory.add(basketball);
        
        ProductCategory skateboard = new ProductCategory();
        skateboard.setName("Skateboard");
        skateboard.setSlug("skateboard");
        skateboard.setParent(sneakers);
        productsCategory.add(skateboard);
        
        ProductCategory clothes = new ProductCategory();
        clothes.setName("Ropa");
        clothes.setSlug("clothes");
        productsCategory.add(clothes);
        
        ProductCategory jackets = new ProductCategory();
        jackets.setName("Chaquetas y chalecos");
        jackets.setSlug("jackets_and_waistcoats");
        jackets.setParent(clothes);
        productsCategory.add(jackets);

        ProductCategory graphicHoodie = new ProductCategory();
        graphicHoodie.setName("Sudadera con Gráfico");
        graphicHoodie.setSlug("sudadera_con_grafico");
        graphicHoodie.setParent(clothes);
        productsCategory.add(graphicHoodie);
        
        ProductCategory tennisPole = new ProductCategory();
        tennisPole.setName("Polo de tenis");
        tennisPole.setSlug("polo_de_tenis");
        tennisPole.setParent(clothes);
        productsCategory.add(tennisPole);
        
        ProductCategory shirt = new ProductCategory();
        shirt.setName("Camiseta");
        shirt.setSlug("camiseta");
        shirt.setParent(clothes);
        productsCategory.add(shirt);
        
        ProductCategory trousers = new ProductCategory();
        trousers.setName("Pantalones");
        trousers.setSlug("trousers");
        trousers.setParent(clothes);
        productsCategory.add(trousers);
        
        
        try{
            productCategoryRepository.deleteAll();
            productCategoryRepository.save(productsCategory);
        } catch(Exception e){
            logger.error(e.getMessage());
        }
    }
}
