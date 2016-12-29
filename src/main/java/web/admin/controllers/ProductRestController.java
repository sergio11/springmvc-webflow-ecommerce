package web.admin.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import persistence.models.Product;
import persistence.repositories.ProductRepository;
import persistence.specifications.ProductFilterSpecification;
import web.models.DataTableProductInput;

/**
 *
 * @author sergio
 */
@RestController
@RequestMapping("/admin/products")
public class ProductRestController {
    
    private static Logger logger = LoggerFactory.getLogger(ProductRestController.class);
    
    @Autowired
    private ProductRepository productRepository;
    
    @JsonView(DataTablesOutput.View.class)
    @GetMapping("/data")
    public DataTablesOutput<Product> all(final @Valid DataTableProductInput input) {
        return productRepository.findAll(input, new ProductFilterSpecification(input.getFilterProduct()));
    }
}
