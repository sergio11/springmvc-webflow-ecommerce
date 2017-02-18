package config.mahout;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.PlusAnonymousUserDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author sergio
 */
@Configuration
public class RecommenderConfig {
    
    @Bean(name = "AnonymousViewHistoryRecommender")
    @Qualifier("AnonymousViewHistoryRecommender")
    private Recommender provideAnonymousViewHistoryRecommender(
            @Qualifier("BooleanPrefDataModel") DataModel dataModel) throws TasteException {
        PlusAnonymousUserDataModel plusAnonymouDataModel = new PlusAnonymousUserDataModel(dataModel);
        UserSimilarity userSimilarity = new TanimotoCoefficientSimilarity(plusAnonymouDataModel);
        UserNeighborhood neighborhood = new NearestNUserNeighborhood(3,
                userSimilarity, plusAnonymouDataModel);
        Recommender recommender = new GenericUserBasedRecommender(plusAnonymouDataModel,
                neighborhood, userSimilarity);
        return new CachingRecommender(recommender);
    }
}
