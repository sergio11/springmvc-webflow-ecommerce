package persistence.models;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

/**
 *
 * @author sergio
 */
@Entity
@Table(name = "REVIEWS")
public class Review implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(DataTablesOutput.View.class)
    private Long id;
   
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @JsonView(DataTablesOutput.View.class)
    private Date createAt = new Date();
    
    @Column(nullable = false)
    @JsonView(DataTablesOutput.View.class)
    private Float rating = 0f;
    
    @Lob
    @Column(nullable = false, columnDefinition="clob")
    private String body;
    
    @ManyToOne(optional = false)
    @JsonView(DataTablesOutput.View.class)
    private User user;
    
    @Enumerated(EnumType.STRING)
    @JsonView(DataTablesOutput.View.class)
    private ReviewStatusEnum status;
    
    @ManyToOne(optional=false)
    private Product product;
   
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
    
    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ReviewStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ReviewStatusEnum status) {
        this.status = status;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
        product.addReview(this);
    }
}
