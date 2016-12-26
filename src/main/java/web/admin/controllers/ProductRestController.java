package web.admin.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import persistence.models.Product;
import persistence.repositories.ProductRepository;

/**
 *
 * @author sergio
 */
@RestController
@RequestMapping("/admin/products")
public class ProductRestController {
    
    @Autowired
    private ProductRepository productRepository;
    
    
    @JsonView(DataTablesOutput.View.class)
    @GetMapping("/data")
    public DataTablesOutput<Product> all(@Valid DataTablesInput input) {
        return productRepository.findAll(input);
    }
}
