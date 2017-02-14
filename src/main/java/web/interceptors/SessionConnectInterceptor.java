package web.interceptors;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.support.ChannelInterceptorAdapter;

public class SessionConnectInterceptor extends ChannelInterceptorAdapter {

    private static Logger logger = LoggerFactory.getLogger(SessionConnectInterceptor.class);

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        MessageHeaders headers = message.getHeaders();
        SimpMessageType type = (SimpMessageType) headers.get("simpMessageType");
        String simpSessionId = (String) headers.get("simpSessionId");
        if (type == SimpMessageType.CONNECT) {
            Principal principal = (Principal) headers.get("simpUser");
            logger.debug("WsSession " + simpSessionId + " is connected for user " + principal.getName());
        } else if (type == SimpMessageType.DISCONNECT) {
            logger.debug("WsSession " + simpSessionId + " is disconnected");
        }
        return message;
    }

}
