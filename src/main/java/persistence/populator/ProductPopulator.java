package persistence.populator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
import persistence.models.ConsumerTypeEnum;
import persistence.models.Product;
import persistence.models.ProductLine;
import persistence.models.ProductStatusEnum;
import persistence.models.Review;
import persistence.models.ReviewStatusEnum;
import persistence.models.User;
import persistence.repositories.ProductCategoryRepository;
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
    
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    
    @Order(4)
    @EventListener(ContextRefreshedEvent.class)
    @Transactional
    public void contextRefreshedEvent() {
        logger.info("SETUP PRODUCT INIT DATA");
       
        List<Product> products = new ArrayList();
        
        // -- Product Number 1
        
        Calendar c = GregorianCalendar.getInstance();
        c.add(Calendar.DATE, 3);
        
        Product product = new Product();
        product.setName("Nike Air Max 2017");
        product.setConsumerType(ConsumerTypeEnum.MAN);
        product.setPrice(190.0);
        product.setAvailableFrom(c.getTime());
        product.setShortDescription("Las zapatillas de running Nike Air Max 2017 para hombre, diseñadas a la perfección con la sujeción y la transpirabilidad .");
        product.setDescription("Las zapatillas de running Nike Air Max 2017 para hombre, diseñadas a la perfección con la sujeción y la transpirabilidad adecuadas donde más lo necesitas, cuentan con una parte superior Flymesh que se combina con la suave amortiguación de la unidad Max Air de longitud completa.");
        product.setStatus(ProductStatusEnum.PUBLISHED);
        product.setCategory(productCategoryRepository.findBySlug("running"));
        
        logger.info("Product To Save: " + product.toString());
        
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
        
        products.add(product);
        
        // -- Product Number 2
        
        Product product2 = new Product();
        product2.setName("Nike Metcon DSX Flyknit");
        product2.setConsumerType(ConsumerTypeEnum.MAN);
        product2.setPrice(150.0);
        product2.setAvailableFrom(c.getTime());
        product2.setShortDescription("Las ligeras zapatillas de entrenamiento Nike Metcon DSX Flyknit para hombre son perfectas para tus entrenamientos más exigentes.");
        product2.setDescription("Las ligeras zapatillas de entrenamiento Nike Metcon DSX Flyknit para hombre son perfectas para tus entrenamientos más exigentes, desde ejercicios de pared a escalada de cuerdas o sprints y levantamiento de pesas.");
        product2.setStatus(ProductStatusEnum.NOT_PUBLISHED);
        product.setCategory(productCategoryRepository.findBySlug("football"));
        
        ProductLine line4 = new ProductLine();
        line4.setDescription("Amarillo Voltio");
        line4.setImage("test4.jpg");
        line4.setProduct(product2);
        line4.setStock(5);
        
        ProductLine line5 = new ProductLine();
        line5.setDescription("Rojo Universitario");
        line5.setImage("test5.jpg");
        line5.setProduct(product2);
        line5.setStock(2);
        
        products.add(product2);
        
        // -- Product Number 3
        
        Product product3 = new Product();
        product3.setName("Nike Sportswear Modern");
        product3.setConsumerType(ConsumerTypeEnum.WOMAN);
        product3.setPrice(60.0);
        product3.setAvailableFrom(c.getTime());
        product3.setShortDescription("La sudadera con gráfico Nike Sportswear Modern para mujer presenta un diseño ergonómico para un movimiento natural.");
        product3.setDescription("La sudadera con gráfico Nike Sportswear Modern para mujer presenta un diseño ergonómico para un movimiento natural.");
        product3.setStatus(ProductStatusEnum.PUBLISHED);
        product3.setCategory(productCategoryRepository.findBySlug("sudadera_con_grafico"));
        
        ProductLine line6 = new ProductLine();
        line6.setDescription("Carbono jaspeado");
        line6.setImage("test6.jpg");
        line6.setProduct(product3);
        line6.setStock(5);
        
        ProductLine line7 = new ProductLine();
        line7.setDescription("Carbono jaspeado");
        line7.setImage("test7.jpg");
        line7.setProduct(product3);
        line7.setStock(5);
        
        products.add(product3);
        
        // -- Product Number 4
        
        Product product4 = new Product();
        product4.setName("Nike Sportswear Advance 15");
        product4.setConsumerType(ConsumerTypeEnum.WOMAN);
        product4.setPrice(80.0);
        product4.setAvailableFrom(c.getTime());
        product4.setShortDescription("La chaqueta Nike Sportswear Advance 15 para mujer.");
        product4.setDescription("La chaqueta Nike Sportswear Advance 15 para mujer actualiza la prenda básica.");
        product4.setStatus(ProductStatusEnum.PUBLISHED);
        product4.setCategory(productCategoryRepository.findBySlug("jackets_and_waistcoats"));
        
        ProductLine line8 = new ProductLine();
        line8.setDescription("Blanco/Negro");
        line8.setImage("test13.jpg");
        line8.setProduct(product4);
        line8.setStock(5);
        
        ProductLine line9 = new ProductLine();
        line9.setDescription("Carbono jaspeado");
        line9.setImage("test14.jpg");
        line9.setProduct(product4);
        line9.setStock(5);
        
        products.add(product4);
        
        // -- Product Number 5
        
        Product product5 = new Product();
        product5.setName("NikeCourt Roger Federer Advantage");
        product5.setConsumerType(ConsumerTypeEnum.MAN);
        product5.setPrice(70.0);
        product5.setAvailableFrom(c.getTime());
        product5.setShortDescription("El polo de tenis NikeCourt Roger Federer Advantage para hombre proporciona una mayor transpirabilidad en toda la superficie.");
        product5.setDescription("El polo de tenis NikeCourt Roger Federer Advantage para hombre proporciona una mayor transpirabilidad en toda la superficie para ayudar a mantenerte fresco.");
        product5.setStatus(ProductStatusEnum.PUBLISHED);
        product5.setCategory(productCategoryRepository.findBySlug("polo_de_tenis"));
        
        ProductLine line10 = new ProductLine();
        line10.setDescription("Carbono jaspeado");
        line10.setImage("test8.jpg");
        line10.setProduct(product5);
        line10.setStock(5);
        
        products.add(product5);
        
        // -- Product Number 6
        
        Product product6 = new Product();
        product6.setName("NikeCourt Advantage");
        product6.setConsumerType(ConsumerTypeEnum.MAN);
        product6.setPrice(55.0);
        product6.setAvailableFrom(c.getTime());
        product6.setShortDescription("Con un ajuste actualizado más ceñido y una comodidad fresca");
        product6.setDescription("Con un ajuste actualizado más ceñido y una comodidad fresca, el polo de tenis gráfico NikeCourt Advantage Premier para hombre te ayuda a centrarte en el rendimiento.");
        product6.setStatus(ProductStatusEnum.PUBLISHED);
        product6.setCategory(productCategoryRepository.findBySlug("polo_de_tenis"));
        
        ProductLine line11 = new ProductLine();
        line11.setDescription("Blanco/Negro/Negro");
        line11.setImage("test9.jpg");
        line11.setProduct(product6);
        line11.setStock(5);
        
        products.add(product6);
        
        // -- Product Number 7
        
        Product product7 = new Product();
        product7.setName("NikeCourt Roger Federer");
        product7.setConsumerType(ConsumerTypeEnum.MAN);
        product7.setPrice(35.0);
        product7.setAvailableFrom(c.getTime());
        product7.setShortDescription("La camiseta NikeCourt Roger Federer para hombre está confeccionada con un tejido puro de algodón que ofrece una comodidad duradera para dentro y fuera de la pista.");
        product7.setDescription("La camiseta NikeCourt Roger Federer para hombre está confeccionada con un tejido puro de algodón que ofrece una comodidad duradera para dentro y fuera de la pista.");
        product7.setStatus(ProductStatusEnum.PUBLISHED);
        product7.setCategory(productCategoryRepository.findBySlug("camiseta"));
        
        ProductLine line12 = new ProductLine();
        line12.setDescription("Gris lobo/Negro");
        line12.setImage("test10.jpg");
        line12.setProduct(product7);
        line12.setStock(5);
        
        ProductLine line13 = new ProductLine();
        line13.setDescription("Negro/Verde estadio");
        line13.setImage("test11.jpg");
        line13.setProduct(product7);
        line13.setStock(5);
        
        products.add(product7);
        
        // -- Product Number 8
        
        Product product8 = new Product();
        product8.setName("NikeCourt Dry");
        product8.setConsumerType(ConsumerTypeEnum.MAN);
        product8.setPrice(35.0);
        product8.setAvailableFrom(c.getTime());
        product8.setShortDescription("La camiseta de tirantes de tenis NikeCourt Dry para hombre ayuda a mantenerte fresco y cómodo durante los entrenamientos más intensos.");
        product8.setDescription("La camiseta de tirantes de tenis NikeCourt Dry para hombre ayuda a mantenerte fresco y cómodo durante los entrenamientos más intensos.");
        product8.setStatus(ProductStatusEnum.PUBLISHED);
        product8.setCategory(productCategoryRepository.findBySlug("camiseta"));
        
        ProductLine line14 = new ProductLine();
        line14.setDescription("Negro/Blanco");
        line14.setImage("test12.jpg");
        line14.setProduct(product8);
        line14.setStock(5);
        
        ProductLine line15 = new ProductLine();
        line15.setDescription("Verde estadio/Negro");
        line15.setImage("test15.jpg");
        line15.setProduct(product8);
        line15.setStock(5);
        
        ProductLine line16 = new ProductLine();
        line16.setDescription("Blanco/Negro");
        line16.setImage("test16.jpg");
        line16.setProduct(product8);
        line16.setStock(5);
        
        products.add(product8);
        
        // -- Product Number 9
        
        Product product9 = new Product();
        product9.setName("NikeCourt Heritage Logo");
        product9.setConsumerType(ConsumerTypeEnum.MAN);
        product9.setPrice(25.0);
        product9.setAvailableFrom(c.getTime());
        product9.setShortDescription("La camiseta NikeCourt Heritage Logo para hombre está confeccionada con un tejido de algodón puro para ofrecer durabilidad y una mayor comodidad durante todo el día.");
        product9.setDescription("La camiseta NikeCourt Heritage Logo para hombre está confeccionada con un tejido de algodón puro para ofrecer durabilidad y una mayor comodidad durante todo el día.");
        product9.setStatus(ProductStatusEnum.PUBLISHED);
        product9.setCategory(productCategoryRepository.findBySlug("camiseta"));
        
        ProductLine line17 = new ProductLine();
        line17.setDescription("Blanco");
        line17.setImage("test17.jpg");
        line17.setProduct(product9);
        line17.setStock(5);
        
        products.add(product9);
        
        // -- Product Number 10
        
        Product product10 = new Product();
        product10.setName("NikeCourt Heritage Loud");
        product10.setConsumerType(ConsumerTypeEnum.MAN);
        product10.setPrice(25.0);
        product10.setAvailableFrom(c.getTime());
        product10.setShortDescription("La camiseta NikeCourt Heritage Logo para hombre está confeccionada con un tejido de algodón puro para ofrecer durabilidad y una mayor comodidad durante todo el día.");
        product10.setDescription("La camiseta NikeCourt Heritage Logo para hombre está confeccionada con un tejido de algodón puro para ofrecer durabilidad y una mayor comodidad durante todo el día.");
        product10.setStatus(ProductStatusEnum.PUBLISHED);
        product10.setCategory(productCategoryRepository.findBySlug("camiseta"));
        
        ProductLine line18 = new ProductLine();
        line18.setDescription("Blanco Blanco");
        line18.setImage("test18.jpg");
        line18.setProduct(product10);
        line18.setStock(5);
        
        products.add(product10);
        
        // -- Product Number 10
        
        Product product11 = new Product();
        product11.setName("NikeCourt Rafael Nadal");
        product11.setConsumerType(ConsumerTypeEnum.MAN);
        product11.setPrice(35.0);
        product11.setAvailableFrom(c.getTime());
        product11.setShortDescription("La camiseta NikeCourt Rafael Nadal para hombre está confeccionada con tejido puro de algodón que ofrece una comodidad duradera para dentro y fuera de la pista.");
        product11.setDescription("La camiseta NikeCourt Rafael Nadal para hombre está confeccionada con tejido puro de algodón que ofrece una comodidad duradera para dentro y fuera de la pista.");
        product11.setStatus(ProductStatusEnum.PUBLISHED);
        product11.setCategory(productCategoryRepository.findBySlug("camiseta"));
        
        ProductLine line19 = new ProductLine();
        line19.setDescription("Super bLANCO");
        line19.setImage("test19.jpg");
        line19.setProduct(product11);
        line19.setStock(5);
        
        ProductLine line20 = new ProductLine();
        line20.setDescription("Blanco Claro");
        line20.setImage("test20.jpg");
        line20.setProduct(product11);
        line20.setStock(5);
        
        ProductLine line21 = new ProductLine();
        line21.setDescription("Blanco Sucio");
        line21.setImage("test21.jpg");
        line21.setProduct(product11);
        line21.setStock(5);
        
        products.add(product11);
        
        
        try{
            productRepository.deleteAll();
            productRepository.save(products);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
