package persistence.models;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

/**
 *
 * @author sergio
 */
@Entity
@Table(name="taste_preferences")
public class TastePreferences implements Serializable {
    
    @EmbeddedId
    private TastePreferencesId tastePreferencesId = new TastePreferencesId();
    @MapsId("userId")
    @ManyToOne(fetch=FetchType.EAGER)
    private User user;
    @MapsId("itemId")
    @ManyToOne(fetch=FetchType.EAGER)
    private Product product;
    @Column(nullable = false)
    private Float preference;
    
    public TastePreferences(){}

    public TastePreferences(User user, Product product, Float preference) {
        this.tastePreferencesId = new TastePreferencesId(user.getId(), product.getId());
        this.user = user;
        this.product = product;
        this.preference = preference;
    }
    
    public TastePreferencesId getTastePreferencesId() {
        return tastePreferencesId;
    }

    public void setTastePreferencesId(TastePreferencesId tastePreferencesId) {
        this.tastePreferencesId = tastePreferencesId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Float getPreference() {
        return preference;
    }

    public void setPreference(Float preference) {
        this.preference = preference;
    }
    
    @Embeddable
    public class TastePreferencesId implements Serializable { 
        
        private static final long serialVersionUID = -2834827403836993112L;

        @Column(name = "user_id")
        private Long userId;
        @Column(name = "item_id")
        private Long itemId;

        public TastePreferencesId(){}
        
        public TastePreferencesId(Long userId, Long itemId) {
            this.userId = userId;
            this.itemId = itemId;
        }
        
        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public Long getItemId() {
            return itemId;
        }

        public void setItemId(Long itemId) {
            this.itemId = itemId;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 13 * hash + Objects.hashCode(this.userId);
            hash = 13 * hash + Objects.hashCode(this.itemId);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final TastePreferencesId other = (TastePreferencesId) obj;
            if (!Objects.equals(this.userId, other.userId)) {
                return false;
            }
            if (!Objects.equals(this.itemId, other.itemId)) {
                return false;
            }
            return true;
        }
       

        @Override
        public String toString() {
            return "TastePreferencesId{" + "userId=" + userId + ", itemId=" + itemId + '}';
        }
    }
    
}
