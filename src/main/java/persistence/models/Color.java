/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence.models;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author sergio
 */
@Entity
@Table(name="COLORS")
public class Color {
    
    @Id
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String name;
    
    @OneToMany(cascade = CascadeType.ALL)
    private Set<ProductColor> productColors = new HashSet();

    public Color(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public Color(){}

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

    public Set<ProductColor> getProductColors() {
        return productColors;
    }

    public void setProductColors(Set<ProductColor> productColors) {
        this.productColors = productColors;
    }
    @Override
    public String toString() {
        return "ProductColor{" + "id=" + id + ", name=" + name + '}';
    }
}
