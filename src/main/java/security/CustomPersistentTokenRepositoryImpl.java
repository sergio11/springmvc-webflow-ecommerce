package security;

import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import persistence.models.RememberMeToken;
import persistence.repositories.RememberMeTokenRepository;

/**
 * @author sergio
 */
public class CustomPersistentTokenRepositoryImpl implements PersistentTokenRepository {
    
    private static Logger logger = LoggerFactory.getLogger(CustomPersistentTokenRepositoryImpl.class);
    
    private RememberMeTokenRepository rememberMeTokenRepository;

    public CustomPersistentTokenRepositoryImpl(RememberMeTokenRepository rememberMeTokenRepository) {
        this.rememberMeTokenRepository = rememberMeTokenRepository;
    }
    
    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        RememberMeToken newToken = new RememberMeToken(token);
        logger.info("Create new Token ..." + newToken.toString());
        this.rememberMeTokenRepository.save(newToken);
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        RememberMeToken token = rememberMeTokenRepository.findBySeries(series);
        logger.info("update Token ..." + token.toString());
        if (token != null) {
            token.setTokenValue(tokenValue);
            token.setDate(lastUsed);
            this.rememberMeTokenRepository.save(token);
        }
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        RememberMeToken token = rememberMeTokenRepository.findBySeries(seriesId);
        logger.info("getTokenForSeries ..." + token.toString());
        return new PersistentRememberMeToken(token.getUsername(), token.getSeries(), token.getTokenValue(), token.getDate());
    }

    @Override
    public void removeUserTokens(String username) {
        List<RememberMeToken> tokens = rememberMeTokenRepository.findByUsername(username);
        logger.info("removeUserTokens");
        rememberMeTokenRepository.delete(tokens);
    }
    
}
