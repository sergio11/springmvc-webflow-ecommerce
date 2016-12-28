package persistence.models;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import persistence.constraints.ValidateDateRange;

/**
 *
 * @author sergio
 */
@Entity
@Table(name = "PRODUCTS")
@Inheritance(strategy = InheritanceType.JOINED)
@ValidateDateRange(start="availableFrom", end="availableTo", message="{product.daterange.invalid}")
public class Product implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(DataTablesOutput.View.class)
    private Long id;
    
    @NotBlank(message="{product.name.notnull}")
    @Size(min=5, max=80, message="{product.name.size}")
    @Column(nullable = false, unique = true, length = 80)
    @JsonView(DataTablesOutput.View.class)
    private String name;
    
    @Column(nullable = false, precision=5, scale=2)
    @DecimalMax(value="999.99", inclusive=true, message="{product.price.max}")
    @DecimalMin(value="00.00", message="{product.price.min}")
    @JsonView(DataTablesOutput.View.class)
    private Double price;
    
    @NotBlank(message="{product.description.notnull}")
    @Size(min=30, max=300, message="{product.description.size}")
    @Column(nullable = false, length = 300)
    private String description;
    
    @NotBlank(message="{product.shortDescription.notnull}")
    @Size(min=30, max=200, message="{product.shortDescription.size}")
    @Column(nullable = false, length = 200)
    private String shortDescription;
    
    @NotBlank(message="{product.availableFrom.notnull}")
    @Column(nullable = false)
    private long availableFrom;
    
    @NotBlank(message="{product.availableTo.notnull}")
    @Column(nullable = false)
    private long availableTo;
    
    @Enumerated(EnumType.STRING)
    private ConsumerTypeEnum consumerType;
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<Model> models = new ArrayList();
    
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Review> reviews = new HashSet();
    
    @Column(nullable = false)
    @JsonView(DataTablesOutput.View.class)
    private Date createAt = new Date();
    
    @Enumerated(EnumType.STRING)
    @JsonView(DataTablesOutput.View.class)
    private ProductStatusEnum status;
    
    @Lob
    @Column(nullable = true)
    private String completeDesc;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public Date getAvailableFrom() {
        return new Date(availableFrom);
    }

    public void setAvailableFrom(Date availableFrom) {
        this.availableFrom = availableFrom.getTime();
    }

    public Date getAvailableTo() {
        return new Date(availableTo);
    }

    public void setAvailableTo(Date availableTo) {
        this.availableTo = availableTo.getTime();
    }
    
    public ConsumerTypeEnum getConsumerType() {
        return consumerType;
    }

    public void setConsumerType(ConsumerTypeEnum consumerType) {
        this.consumerType = consumerType;
    }

    public List<Model> getModels() {
        return models;
    }

    public void setModels(List<Model> models) {
        this.models = models;
    }
    
    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public ProductStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ProductStatusEnum status) {
        this.status = status;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getCompleteDesc() {
        return completeDesc;
    }
    
    public void setCompleteDesc(String completeDesc) {
        this.completeDesc = completeDesc;
    }

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", name=" + name + ", price=" + price + ", description=" + description + ", shortDescription=" + shortDescription + ", availableFrom=" + availableFrom + ", availableTo=" + availableTo + ", consumerType=" + consumerType + ", models=" + models + ", reviews=" + reviews + ", createAt=" + createAt + ", status=" + status + ", completeDesc=" + completeDesc + '}';
    }
    
    
}
