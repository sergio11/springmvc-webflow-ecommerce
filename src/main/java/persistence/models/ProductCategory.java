package persistence.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * @author sergio
 */
@Entity
@Table(name = "PRODUCT_CATEGORIES")
public class ProductCategory implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String slug;
    
    @Column(nullable = false, unique = true)
    private String name;
    
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    private ProductCategory parent;
    
    @OneToMany(mappedBy="parent", fetch = FetchType.EAGER)
    private Set<ProductCategory> subcategories = new HashSet();
    
    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    private Set<Product> products = new HashSet();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public ProductCategory getParent() {
        return parent;
    }

    public void setParent(ProductCategory parent) {
        this.parent = parent;
        parent.addSubcategory(this);
    }

    public Set<ProductCategory> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(Set<ProductCategory> subcategories) {
        this.subcategories = subcategories;
    }
    
    public void addSubcategory(ProductCategory category) {
        if(!subcategories.contains(category)) {
            subcategories.add(category);
            category.setParent(this);
        }
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
    
    public void addProduct(Product product) {
        if(!products.contains(product)) {
            products.add(product);
            product.setCategory(this);
        }
    }

    @Override
    public String toString() {
        return "ProductCategory{" + "id=" + id + ", slug=" + slug + ", name=" + name + '}';
    }
}
