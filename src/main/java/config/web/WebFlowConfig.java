package config.web;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.webflow.config.AbstractFlowConfiguration;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;
import org.springframework.webflow.executor.FlowExecutor;
import org.springframework.webflow.mvc.builder.MvcViewFactoryCreator;
import org.springframework.webflow.mvc.servlet.FlowHandlerAdapter;
import org.springframework.webflow.mvc.servlet.FlowHandlerMapping;
import org.springframework.webflow.security.SecurityFlowExecutionListener;

/**
 * Spring Web Config with Thymeleaf support
 * @author sergio
 */
@Configuration
public class WebFlowConfig extends AbstractFlowConfiguration {
    
    @Autowired
    private ViewResolver viewResolver;
    
    @Autowired
    private Environment env;
    
    @Bean
    public MvcViewFactoryCreator mvcViewFactoryCreator() {
        MvcViewFactoryCreator factoryCreator = new MvcViewFactoryCreator();
        factoryCreator.setViewResolvers(Arrays.<ViewResolver>asList(viewResolver));
        factoryCreator.setUseSpringBeanBinding(true);
        return factoryCreator;
    }
    
    @Bean
    public FlowBuilderServices flowBuilderServices() {
        return getFlowBuilderServicesBuilder()
                .setViewFactoryCreator(mvcViewFactoryCreator())
                .setDevelopmentMode(true)
                .build();
    }
   
    @Bean
    public FlowDefinitionRegistry flowRegistry() {
        return getFlowDefinitionRegistryBuilder()
            .setFlowBuilderServices(flowBuilderServices())
            .setBasePath(env.getProperty("flows.base.path"))
            .addFlowLocationPattern(env.getProperty("flows.location.pattern"))
            .build();
    }
    
    @Bean
    public FlowExecutor flowExecutor() {
        return getFlowExecutorBuilder(flowRegistry())
                .addFlowExecutionListener(new SecurityFlowExecutionListener() , "*")
                .build();
    }
    
    @Bean
    public FlowHandlerMapping flowHandlerMapping() {
        FlowHandlerMapping mapping = new FlowHandlerMapping();
	mapping.setOrder(1);
        mapping.setFlowRegistry(flowRegistry());
	return mapping;
    }
    
    @Bean
    public FlowHandlerAdapter flowHandlerAdapter() {
        FlowHandlerAdapter adapter = new FlowHandlerAdapter();
        adapter.setFlowExecutor(flowExecutor());
        return adapter;
    }
    
}
