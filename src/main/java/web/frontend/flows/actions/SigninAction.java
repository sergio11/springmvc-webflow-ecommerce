package web.frontend.flows.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;
import web.models.SigninCredentials;

/**
 * @author sergio
 */
@Component
public class SigninAction extends AbstractAction {
    
    private static Logger logger = LoggerFactory.getLogger(SigninAction.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    protected Event doExecute(RequestContext context) throws Exception {
        MessageContext messageContext = context.getMessageContext();
        MessageBuilder builder = new MessageBuilder();
        try {
            SigninCredentials signinCredentials = (SigninCredentials) context.getFlowScope().get("signinCredentials");
            logger.info("Signin Credentials: " + signinCredentials.toString());
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinCredentials.getUsername(), signinCredentials.getPassword()));
            if (authenticate.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(authenticate);
                return success();
            } else {
                messageContext.addMessage(
                        builder
                                .error()
                                .code("frontend.checkout.signin.failed")
                                .build()
                );
                return error();
            }
        } catch (AuthenticationException e) {
            messageContext.addMessage(
                    builder
                            .error()
                            .code("frontend.checkout.signin.failed")
                            .build()
            );
            logger.error(e.getMessage());
            return error();
        }
    }
}
