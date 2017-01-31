package web.interceptors;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import persistence.models.Product;
import web.services.ProductService;

/**
 * @author sergio
 */
@Component
public class LoadNewProductsHandlerInterceptor extends HandlerInterceptorAdapter {
    
    @Autowired
    private ProductService productService;
    
    // The name under which the categories are localled
    public static final String ATTRIBUTE_NAME = "newproducts";
    
    @Override
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response,
            Object handler, ModelAndView modelAndView) throws Exception {

        if (modelAndView != null) {
            List<Product> products = productService.getNewProducts();
            modelAndView.getModelMap().
                    addAttribute(ATTRIBUTE_NAME, products);
        }
    }
}
