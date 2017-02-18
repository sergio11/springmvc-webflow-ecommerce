package web.recommendation.recommender;

import java.util.List;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.PlusAnonymousUserDataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import persistence.repositories.ProductRepository;
import persistence.repositories.UserRepository;

/**
 * @author sergio
 */
public class AnonymousRecommenderDecorator extends CollaborativeFilteringRecommender {
   
    private final PlusAnonymousUserDataModel plusAnonymousModel;

    public AnonymousRecommenderDecorator(PlusAnonymousUserDataModel plusAnonymousModel, UserRepository userRepository, ProductRepository productRepository) throws TasteException {
        super(plusAnonymousModel, userRepository, productRepository);
        this.plusAnonymousModel= plusAnonymousModel;
    }
    
    public synchronized List<RecommendedItem> recommend(PreferenceArray anonymousUserPrefs, int howMany) throws TasteException {
        plusAnonymousModel.setTempPrefs(anonymousUserPrefs);
        List<RecommendedItem> recommendations 
                = recommend(PlusAnonymousUserDataModel.TEMP_USER_ID, howMany, null);
        plusAnonymousModel.clearTempPrefs();
        return recommendations;
    }
    
}
