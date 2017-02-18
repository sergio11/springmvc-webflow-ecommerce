package web.services;

import java.util.List;


/**
 * @author sergio
 */
public interface RecommenderService {
    void addProductViewedToAnonymousUserHistory(Long productId);
    void addProductViewedToUserHistory(Long userId, Long productId);
    List<Long> recommendForAnonymousUser(int howMany);
    List<Long> recommendForUser(Long user, int howMany);
}
