package web.admin.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import persistence.models.ProductCategory;
import persistence.repositories.ProductCategoryRepository;

/**
 * @author sergio
 */
@Component
public class StringProductCategoryConverter implements Converter<String, ProductCategory>{
    
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Override
    public ProductCategory convert(String id) {
        return productCategoryRepository.findOne(Long.valueOf(id));
    }
}
