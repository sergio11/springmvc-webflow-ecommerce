package web.admin.controllers.rest;

import com.fasterxml.jackson.annotation.JsonView;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import persistence.models.Order;
import persistence.repositories.OrderRepository;
import persistence.specifications.OrderFilterSpecification;
import web.models.datatables.DataTableOrderInput;

/**
 * @author sergio
 */
@RestController
@RequestMapping("/admin/orders")
public class OrderRestController {
    
    private static Logger logger = LoggerFactory.getLogger(OrderRestController.class);
    
    @Autowired
    private OrderRepository orderRepository;

    @JsonView(DataTablesOutput.View.class)
    @GetMapping("/data")
    public DataTablesOutput<Order> all(final @Valid DataTableOrderInput input) {
        return orderRepository.findAll(input, new OrderFilterSpecification(input.getFilter()));
    }
    
}
