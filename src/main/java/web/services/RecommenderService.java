package web.services;

import java.util.List;


/**
 * @author sergio
 */
public interface RecommenderService {
    void addAnonymousPref(Long productId);
    List<Long> recommendForAnonymousUser(int howMany);
}
