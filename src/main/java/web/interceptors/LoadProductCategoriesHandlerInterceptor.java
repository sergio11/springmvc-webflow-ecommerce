package web.interceptors;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import persistence.models.ProductCategory;
import persistence.repositories.ProductCategoryRepository;

/**
 * @author sergio
 */
@Component
public class LoadProductCategoriesHandlerInterceptor extends HandlerInterceptorAdapter {
    
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    
    // The name under which the categories are localled
    public static final String ATTRIBUTE_NAME = "categories";
    
    @Override
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response,
            Object handler, ModelAndView modelAndView) throws Exception {

        if (modelAndView != null) {
            List<ProductCategory> categories = productCategoryRepository.findByParentIsNull();
            modelAndView.getModelMap().
                    addAttribute(ATTRIBUTE_NAME, categories);
        }
    }
}
