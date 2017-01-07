package web.listeners;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Component;
import persistence.models.User;
import web.events.user.ChangePasswordEvent;

/**
 * @author sergio
 */
@Component
public class UserListener {
    
    private static Logger logger = LoggerFactory.getLogger(UserListener.class);
    
    @Autowired
    private SessionRegistry sessionRegistry;
    
    @EventListener
    public void handleChangePasswordEvent(ChangePasswordEvent event) {
        logger.info("handleChangePasswordEvent for : " + event.getUsername());
        List<Object> loggedUsers = sessionRegistry.getAllPrincipals();
        logger.info("loggedUsers : " + loggedUsers.size());
        for (Object principal : loggedUsers) {
            if (principal instanceof User) {
                final User loggedUser = (User) principal;
                logger.info("loggedUser : " + loggedUser.getUsername());
                if (event.getUsername().equals(loggedUser.getUsername())) {
                    List<SessionInformation> sessionsInfo = sessionRegistry.getAllSessions(principal, false);
                    if (null != sessionsInfo && sessionsInfo.size() > 0) {
                        for (SessionInformation sessionInformation : sessionsInfo) {
                            logger.info("Exprire now :" + sessionInformation.getSessionId());
                            sessionInformation.expireNow();
                            sessionRegistry.removeSessionInformation(sessionInformation.getSessionId());
                            // User is not forced to re-logging
                        }
                    }
                }
            }
        }
    }
}
