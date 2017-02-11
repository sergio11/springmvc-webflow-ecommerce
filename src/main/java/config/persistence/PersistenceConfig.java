package config.persistence;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.datatables.repository.DataTablesRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@Import(value = { DataSourceConfig.class, VendorAdapterConfig.class, BeanValidationConfiguration.class })
@EnableTransactionManagement
@EnableJpaRepositories(
        repositoryFactoryBeanClass = DataTablesRepositoryFactoryBean.class,
        transactionManagerRef = "transactionManager",
        basePackages = "persistence.repositories"
)
@ComponentScan(value = "persistence.populator" )
public class PersistenceConfig {

    private static final String PACKAGE_TO_SCAN_MODELS = "persistence.models";
    
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean provideEntityManagerFactory(
            DataSource datasource, JpaVendorAdapter jpaVendorAdapter, Properties jpaProperties) {
        LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
        emfb.setDataSource(datasource);
        emfb.setJpaVendorAdapter(jpaVendorAdapter);
        emfb.setPackagesToScan(PACKAGE_TO_SCAN_MODELS);
        emfb.setJpaProperties(jpaProperties);
        return emfb;
    }
    
    @Bean(name="transactionManager")
    public PlatformTransactionManager provideTransactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactory){
        EntityManagerFactory factory = entityManagerFactory.getObject();
        return new JpaTransactionManager(factory);
    }
}
