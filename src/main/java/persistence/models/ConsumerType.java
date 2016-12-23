/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence.models;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author sergio
 */
@Entity
@Table(name="CONSUMER_TYPES")
public class ConsumerType implements Serializable {

    @Id
    private Long id;
    
    @Enumerated(EnumType.STRING)
    @Column(name="type", nullable = false, unique = true)
    private ConsumerTypeEnum type;
    
    @OneToMany(mappedBy = "consumerType")
    private Set<Product> products;
    
    
    
    public ConsumerType(Long id, ConsumerTypeEnum type) {
        this.id = id;
        this.type = type;
    }
    
    public ConsumerType(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ConsumerTypeEnum getType() {
        return type;
    }

    public void setType(ConsumerTypeEnum type) {
        this.type = type;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
    
    @Override
    public String toString() {
        return "ConsumerType{" + "id=" + id + ", type=" + type + '}';
    }
}
