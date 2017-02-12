package config.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.view.jasperreports.JasperReportsMultiFormatView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsViewResolver;

@Configuration
public class JasperConfig {

    @Autowired
    private Environment env;
 
    @Bean
    public JasperReportsViewResolver getJasperReportsViewResolver() {
        JasperReportsViewResolver resolver = new JasperReportsViewResolver();
        resolver.setPrefix(env.getProperty("jasper.reports.path"));
        resolver.setSuffix(".jasper");
        resolver.setReportDataKey("ProductsItemSource");
        resolver.setViewNames(env.getProperty("jasper.reports.view.names"));
        resolver.setViewClass(JasperReportsMultiFormatView.class);
        resolver.setOrder(0);
        return resolver;
    }

}
