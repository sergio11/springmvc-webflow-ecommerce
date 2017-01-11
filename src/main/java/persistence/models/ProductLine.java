package persistence.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author sergio
 */
@Entity
@Table(name = "PRODUCT_LINES")
public class ProductLine implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String image;
    @Min(value = 0, message = "{product.line.stock.min}")
    @Max(value = 200, message = "{product.line.stock.max}")
    @Column(nullable = false)
    private Integer stock = 0;
    @NotBlank(message="{product.line.desc.notnull}")
    @Size(min=5, max=80, message="{product.line.desc.size}")
    private String description;
    @ManyToOne(optional=false, fetch = FetchType.EAGER)
    private Product product;
    @OneToMany(mappedBy = "productLine")
    private List<OrderLine> orderLines = new ArrayList();
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
        product.addProductLine(this);
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    @Override
    public String toString() {
        return "ProductLine{" + "id=" + id + ", image=" + image + ", stock=" + stock + ", description=" + description + '}';
    }
}
