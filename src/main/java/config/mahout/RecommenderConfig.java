package config.mahout;

import config.qualifiers.AnonymousViewHistoryRecommender;
import config.qualifiers.BooleanPrefDataModel;
import config.qualifiers.CollaborativeFilteringRecommender;
import config.qualifiers.RatingDataModel;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.PlusAnonymousUserDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author sergio
 */
@Configuration
@Import(value = { DataModelConfig.class })
public class RecommenderConfig {
    
    @Bean @AnonymousViewHistoryRecommender
    public Recommender provideAnonymousViewHistoryRecommender(
           @BooleanPrefDataModel DataModel dataModel) throws TasteException {
        PlusAnonymousUserDataModel plusAnonymouDataModel = new PlusAnonymousUserDataModel(dataModel);
        UserSimilarity userSimilarity = new TanimotoCoefficientSimilarity(plusAnonymouDataModel);
        UserNeighborhood neighborhood = new NearestNUserNeighborhood(3,
                userSimilarity, plusAnonymouDataModel);
        Recommender recommender = new GenericUserBasedRecommender(plusAnonymouDataModel,
                neighborhood, userSimilarity);
        return recommender;
    }
    
    @Bean @CollaborativeFilteringRecommender
    public Recommender provideCollaborativeFilteringRecommender(
            @RatingDataModel DataModel dataModel) throws TasteException {
        UserSimilarity similarity = new EuclideanDistanceSimilarity(dataModel);
        UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, dataModel);
        Recommender recommender = new GenericUserBasedRecommender(dataModel, neighborhood, similarity);
        return recommender;
    }
}
