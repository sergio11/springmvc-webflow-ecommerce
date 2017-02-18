package web.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.models.anonymous.AnonymousSession;
import web.services.RecommenderService;

/**
 * @author sergio
 */
@Service("recommenderService")
public class RecommenderServiceImpl implements RecommenderService {
    
    @Autowired
    private AnonymousSession anonymousSession; 

    @Override
    public void addAnonymousPref(Long productId) {
        anonymousSession.trackViewedProduct(productId);
    }
}
