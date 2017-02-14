package config.mahout;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.PlusAnonymousUserDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import persistence.repositories.ProductRepository;
import persistence.repositories.UserRepository;
import web.recommendation.recommender.CollaborativeFilteringRecommender;
import web.recommendation.recommender.CollaborativeFilteringWithAnonymousRecommender;

/**
 * @author sergio
 */
@Configuration
public class RecommenderConfig {
    
    @Bean(name = "CollaborativeFilteringRecommender")
    private CollaborativeFilteringRecommender provideCollaborativeFilteringRecommender(
            DataModel dataModel,
            UserRepository userRepository,
            ProductRepository productRepository) throws TasteException{
        return new CollaborativeFilteringRecommender(dataModel, userRepository, productRepository);
    }
    
    @Bean(name = "CollaborativeFilteringWithAnonymousRecommender")
    private CollaborativeFilteringWithAnonymousRecommender provideCollaborativeFilteringWithAnonymousRecommender(
            DataModel dataModel,
            UserRepository userRepository,
            ProductRepository productRepository) throws TasteException{
        return new CollaborativeFilteringWithAnonymousRecommender(new PlusAnonymousUserDataModel(dataModel), userRepository, productRepository);
    }
}
