package web.models.anonymous;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.apache.mahout.cf.taste.model.Preference;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 *
 * @author sergio
 */
@Component("anonymous")
@Scope( value = "session",  proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AnonymousSession implements Serializable {
    
    private Set<Long> trackedProducts = new HashSet<Long>();
    
    public void trackViewedProduct(Long id){
        trackedProducts.add(id);
    }

    public Set<Long> getTrackedProducts() {
        return trackedProducts;
    }

    public void setTrackedProducts(Set<Long> trackedProducts) {
        this.trackedProducts = trackedProducts;
    }
    
    @PostConstruct
    public void init() {
        
    }
}
