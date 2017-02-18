package web.recommendation.recommender;

import java.util.List;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.PlusAnonymousUserDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import persistence.repositories.ProductRepository;
import persistence.repositories.UserRepository;

/**
 * @author sergio
 */
public class AnonymousRecommenderDecorator extends GenericUserBasedRecommender {
   
    public AnonymousRecommenderDecorator(DataModel dataModel, UserNeighborhood neighborhood, UserSimilarity similarity) {
        super(new PlusAnonymousUserDataModel(dataModel), neighborhood, similarity);
    }
    
    
    
    
    
   
    
    /*public synchronized List<RecommendedItem> recommend(PreferenceArray anonymousUserPrefs, int howMany) throws TasteException {
        plusAnonymousModel.setTempPrefs(anonymousUserPrefs);
        List<RecommendedItem> recommendations 
                = recommend(PlusAnonymousUserDataModel.TEMP_USER_ID, howMany, null);
        plusAnonymousModel.clearTempPrefs();
        return recommendations;
    }*/
    
}
