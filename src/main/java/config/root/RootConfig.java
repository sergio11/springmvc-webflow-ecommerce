package config.root;

import config.persistence.PersistenceConfig;
import config.security.SecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 *
 * @author sergio
 */
@Configuration
@PropertySource(value = { "classpath:application.properties" })
@Import(value = { PersistenceConfig.class, SecurityConfig.class })
public class RootConfig {}
