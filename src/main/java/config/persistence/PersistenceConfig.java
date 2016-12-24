package config.persistence;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@Import(value = { DataSourceConfig.class, VendorAdapterConfig.class })
@EnableJpaRepositories(basePackages = "persistence.repositories")
public class PersistenceConfig {

    private static final String PACKAGE_TO_SCAN_MODELS = "persistence.models";
    
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean provideEntityManagerFactory(
            DataSource datasource, JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
        emfb.setDataSource(datasource);
        emfb.setJpaVendorAdapter(jpaVendorAdapter);
        emfb.setPackagesToScan(PACKAGE_TO_SCAN_MODELS);
        return emfb;
    }
    
    @Bean(name="transactionManager")
    public PlatformTransactionManager provideTransactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactory){
        EntityManagerFactory factory = entityManagerFactory.getObject();
        return new JpaTransactionManager(factory);
    }
    
    @Bean(name="repositoryPopulator")
    public Jackson2RepositoryPopulatorFactoryBean  provideJackson2RepositoryPopulatorFactoryBean(){
        Resource sourceData = new ClassPathResource("data.json");
        Jackson2RepositoryPopulatorFactoryBean factory = new Jackson2RepositoryPopulatorFactoryBean();
        factory.setResources(new Resource[] { sourceData });
        return factory;
    }
}
