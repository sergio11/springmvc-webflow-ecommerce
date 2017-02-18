package web.interceptors;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import web.frontend.controllers.ProductController;
import web.services.RecommenderService;
import web.services.UserService;

/**
 * @author sergio
 */
@Component
public class TrackProductsViewedInterceptor extends HandlerInterceptorAdapter {
    
    private static Logger logger = LoggerFactory.getLogger(TrackProductsViewedInterceptor.class);
    
    @Autowired
    private UserService userService;
    @Autowired
    private RecommenderService recommenderService;
    
    @Override
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response,
            Object handler, ModelAndView modelAndView) throws Exception {
       
        final Map<String, String> pathVariables = 
                (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        
        // GET Product line from PathVariable
        Long productLine = Long.valueOf(pathVariables.get(ProductController.PRODUCT_LINE_PATH_VARIABLE));
        
        logger.info("Track Product Line viewed with id: " + productLine);
       
        if(!userService.isAuthenticated()) {
            logger.info("For Anonymous User");
            recommenderService.addAnonymousPref(productLine);
        }
    }
}
