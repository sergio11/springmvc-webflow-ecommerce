package web.services;

import java.util.List;
import persistence.models.ProductLine;
import persistence.models.User;


/**
 * @author sergio
 */
public interface RecommenderService {
    void addProductViewedToAnonymousUserHistory(Long productId);
    void addProductViewedToUserHistory(Long userId, Long productId);
    List<Long> recommendForAnonymousUser(int howMany);
    List<Long> recommendForUser(Long user, int howMany);
    void addPreference(User user, ProductLine productLine, Float preference);
    void removePreference(Long userId, Long productLineId);
}
