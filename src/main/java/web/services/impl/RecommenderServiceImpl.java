package web.services.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Set;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.impl.model.BooleanUserPreferenceArray;
import org.apache.mahout.cf.taste.impl.model.PlusAnonymousUserDataModel;
import org.apache.mahout.cf.taste.recommender.IDRescorer;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import persistence.models.User;
import persistence.repositories.ProductRepository;
import persistence.repositories.UserRepository;
import web.admin.exceptions.UserNotFoundException;
import web.frontend.exceptions.RecommendationException;
import web.models.anonymous.AnonymousSession;
import web.recommendation.rescorers.ConsumerTypeRescorer;
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

    @Autowired
    @Qualifier("CollaborativeFilteringRecommender")
    private Recommender collaborativeFilteringRecommender;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ProductRepository productRepository;

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
        for (int i = 0; i < productsId.length; i++) {
            anonymousPrefs.setItemID(i, productsId[i]);
        }
        PlusAnonymousUserDataModel plusAnonymousUserDataModel
                = (PlusAnonymousUserDataModel) anonymousViewHistoryRecommender.getDataModel();
        plusAnonymousUserDataModel.setTempPrefs(anonymousPrefs);
        try {
            List<RecommendedItem> recommendations = anonymousViewHistoryRecommender.recommend(PlusAnonymousUserDataModel.TEMP_USER_ID, howMany);
            productLinesRecommended = Lists.transform(recommendations, new Function<RecommendedItem, Long>() {
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

    @Override
    public List<Long> recommendForUser(Long userId, int howMany) {
        List<Long> productLinesRecommended = null;
        User user = userRepository.findOne(userId);
        if (user == null) {
            throw new UserNotFoundException();
        }
        IDRescorer rescorer = new ConsumerTypeRescorer(productRepository, user.getGender(), user.getAge());
        try {
            List<RecommendedItem> recommendations = collaborativeFilteringRecommender.recommend(userId, howMany, rescorer);
            productLinesRecommended = Lists.transform(recommendations, new Function<RecommendedItem, Long>() {
                @Override
                public Long apply(RecommendedItem item) {
                    return item.getItemID();
                }
            });
        } catch (TasteException ex) {
            throw new RecommendationException();
        }
        return productLinesRecommended;
    }
}
