package config.web;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;


/**
 * @author sergio
 */
@Configuration
public class ViewConfig implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * Implementation of ITemplateResolver that extends AbstractConfigurableTemplateResolver and resolves templates 
     * using Spring's Resource Resolution mechanism (see ResourceLoader.getResource(String)). 
     * @return ITemplateResolver
     */
    @Bean(name = "templateResolver")
    public ITemplateResolver provideTemplateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setApplicationContext(applicationContext);
        resolver.setPrefix("/WEB-INF/templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        return resolver;
    }

    @Bean(name = "templateEngine")
    public TemplateEngine provideTemplateEngine(ITemplateResolver templateResolver) {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setEnableSpringELCompiler(true);
        engine.setTemplateResolver(templateResolver);
        engine.addDialect(new LayoutDialect());
        engine.addDialect(new SpringSecurityDialect());
        return engine;
    }
    

    /**
     * Implementation of the Spring MVC ViewResolver interface.
     * View resolvers execute after the controller ends its execution. 
     * They receive the name of the view to be processed and are in charge of creating (and configuring) the corresponding View object for it.
     * The View implementations managed by this class are subclasses of AbstractThymeleafView. By default, ThymeleafView is used. 
     * @param templateEngine
     * @return 
     */
    @Bean(name = "viewResolver")
    public ViewResolver provideViewResolver(TemplateEngine templateEngine) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine);
        resolver.setCharacterEncoding("UTF-8");
        return resolver;
    }
}
