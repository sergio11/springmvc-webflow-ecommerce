package security.handlers;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

/**
 * @author sergio
 */
@Component
public class CustomLogoutHandler implements LogoutHandler {
    
    private static Logger logger = LoggerFactory.getLogger(CustomLogoutHandler.class);
    
    @Autowired
    private SessionRegistry sessionRegistry;

    @Override
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        logger.info("User Logout");
        httpServletRequest.getSession().invalidate();
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        List<SessionInformation> userSessions = sessionRegistry.getAllSessions(authentication, true);

        for (SessionInformation session: userSessions) {
            sessionRegistry.removeSessionInformation(session.getSessionId());
        }
    }
    
}
