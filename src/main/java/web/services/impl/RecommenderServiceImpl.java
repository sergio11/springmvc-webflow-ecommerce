package web.services.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Set;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.impl.model.BooleanUserPreferenceArray;
import org.apache.mahout.cf.taste.impl.model.PlusAnonymousUserDataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import web.frontend.exceptions.RecommendationException;
import web.models.anonymous.AnonymousSession;
import web.services.RecommenderService;

/**
 * @author sergio
 */
@Service("recommenderService")
public class RecommenderServiceImpl implements RecommenderService {
    
    @Autowired
    private AnonymousSession anonymousSession;
    
    @Autowired
    @Qualifier("AnonymousViewHistoryRecommender")
    private Recommender anonymousViewHistoryRecommender;

    @Override
    public void addAnonymousPref(Long productId) {
        anonymousSession.trackViewedProduct(productId);
    }

    @Override
    public List<Long> recommendForAnonymousUser(int howMany) {
        List<Long> productLinesRecommended = null;
        Set<Long> trackedProducts = anonymousSession.getTrackedProducts();
        Long[] productsId = trackedProducts.<Long>toArray(new Long[trackedProducts.size()]);
        BooleanUserPreferenceArray anonymousPrefs = new BooleanUserPreferenceArray(productsId.length);
        anonymousPrefs.setUserID(0, PlusAnonymousUserDataModel.TEMP_USER_ID);
        for(int i = 0; i < productsId.length; i++)
            anonymousPrefs.setItemID(i, productsId[i]);
        PlusAnonymousUserDataModel  plusAnonymousUserDataModel = 
                (PlusAnonymousUserDataModel)anonymousViewHistoryRecommender.getDataModel();
        plusAnonymousUserDataModel.setTempPrefs(anonymousPrefs);
        try {
            List<RecommendedItem> recommendations = anonymousViewHistoryRecommender.recommend(PlusAnonymousUserDataModel.TEMP_USER_ID, howMany);
            productLinesRecommended = Lists.transform(recommendations,  new Function<RecommendedItem, Long>() { 
                @Override
                public Long apply(RecommendedItem item) {
                    return item.getItemID();
                }
            });
        } catch (TasteException ex) {
            throw new RecommendationException();
        } finally {
            plusAnonymousUserDataModel.clearTempPrefs();
        }
        return productLinesRecommended;
    }
}
