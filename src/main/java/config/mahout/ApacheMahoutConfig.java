package config.mahout;

import javax.sql.DataSource;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.model.jdbc.SQL92JDBCDataModel;
import org.apache.mahout.cf.taste.model.JDBCDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author sergio
 */
@Configuration
public class ApacheMahoutConfig {
    
    @Autowired
    private DataSource datasource;
    
    @Profile("development")
    @Bean(name = "JDBCDataModel")
    public JDBCDataModel provideSQL92JDBCDataModel(){
        return new SQL92JDBCDataModel(datasource);
    }
    
    @Profile("production")
    @Bean(name = "JDBCDataModel")
    public JDBCDataModel provideMySQLJDBCDataModel(){
        return new MySQLJDBCDataModel(datasource);
    }
}
