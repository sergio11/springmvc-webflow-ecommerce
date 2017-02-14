package web.recommendation.recommender;

import java.util.Collection;
import java.util.List;
import org.apache.mahout.cf.taste.common.Refreshable;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.JDBCDataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.IDRescorer;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import persistence.models.User;
import persistence.repositories.ProductRepository;
import persistence.repositories.UserRepository;
import web.admin.exceptions.UserNotFoundException;
import web.recommendation.rescorers.ConsumerTypeRescorer;

/**
 * @author sergio
 */
@Component
public class UserRecommender implements Recommender {
    
    @Autowired
    private JDBCDataModel dataModel;
    
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    
    private final Recommender delegate;
    
    public UserRecommender() throws TasteException {
        UserSimilarity similarity = new EuclideanDistanceSimilarity(dataModel);
        UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, dataModel);
        delegate = new GenericUserBasedRecommender(dataModel, neighborhood, similarity);
    }
    
    @Override
    public List<RecommendedItem> recommend(long userID, int howMany) throws TasteException, UserNotFoundException {
        User user = userRepository.findOne(userID);
        if(user == null){
            throw new UserNotFoundException();
        }
        IDRescorer rescorer = new ConsumerTypeRescorer(productRepository, user.getGender(), user.getAge());
        return delegate.recommend(userID, howMany, rescorer);
    }

    @Override
    public List<RecommendedItem> recommend(long userID, int howMany, IDRescorer rescorer) throws TasteException {
        return delegate.recommend(userID, howMany, rescorer);
    }

    @Override
    public float estimatePreference(long userID, long itemID) throws TasteException {
        User user = userRepository.findOne(userID);
        if(user == null){
            throw new UserNotFoundException();
        }
        IDRescorer rescorer = new ConsumerTypeRescorer(productRepository, user.getGender(), user.getAge());
        return (float) rescorer.rescore(itemID, delegate.estimatePreference(userID, itemID));
    }

    @Override
    public void setPreference(long userID, long itemID, float value) throws TasteException {
        delegate.setPreference(userID, itemID, value);
    }

    @Override
    public void removePreference(long userID, long itemID) throws TasteException {
        delegate.removePreference(userID, itemID);
    }

    @Override
    public DataModel getDataModel() {
        return delegate.getDataModel();
    }

    @Override
    public void refresh(Collection<Refreshable> alreadyRefreshed) {
        delegate.refresh(alreadyRefreshed);
    }
    
}
