package config.security;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.session.CompositeSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import persistence.models.AuthorityEnum;
import persistence.repositories.RememberMeTokenRepository;
import security.handlers.CustomLogoutHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import security.CustomPersistentTokenRepositoryImpl;

/**
 * @author sergio
 */
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
@EnableWebSecurity
@ComponentScan(value = { "security" })
public class SecurityConfig extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private CustomLogoutHandler logoutHandler;
   
    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .authenticationEventPublisher(authenticationEventPublisher())
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());
    }
    
    @Bean
    public DefaultAuthenticationEventPublisher authenticationEventPublisher() {
        return new DefaultAuthenticationEventPublisher();
    }
    
    /**
     * Security Configuration for Admin zone
     */
    @Configuration
    @Order(1)
    public class AdminConfiguration extends WebSecurityConfigurerAdapter {
 
        @Autowired
        private AuthenticationSuccessHandler successHandler;
        
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                .requestMatchers()
                    .antMatchers("/admin/**")
                .and()
                .authorizeRequests()
                .antMatchers("/admin/users/self/**")
                    .fullyAuthenticated()
                .antMatchers("/admin/**")
                    .hasAuthority(AuthorityEnum.ROLE_ADMIN.name())
                .and()
                .formLogin()
                    .loginPage("/admin/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .successHandler(successHandler)
                    .permitAll()
                .and()
                .logout()
                    .addLogoutHandler(logoutHandler)
                    .logoutRequestMatcher(new AntPathRequestMatcher("/admin/logout"))
                    .logoutSuccessUrl("/admin/login?logout")
                    .deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true)
                .and()
                .exceptionHandling().accessDeniedPage("/admin/403")
                .and()
                .csrf()
                .disable();
        }
    }
    
    
    /**
     * Security Configuration for Frontend zone
     */
    @Configuration
    @Order(2)
    public class FrontendConfiguration extends WebSecurityConfigurerAdapter {

        @Autowired
        private Environment env;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                .antMatchers("/*.*")
                .permitAll()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .defaultSuccessUrl(env.getProperty("redirect.frontend.after.success.login"), true)
                    .permitAll()
                .and()
                .logout()
                    .addLogoutHandler(logoutHandler)
                    .logoutSuccessUrl("/login?logout")
                    .deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true)
                .and()
                .exceptionHandling().accessDeniedPage("/403")
                .and()
                .csrf();
        }
    }
    
    @Configuration
    @Order(3)
    public class GlobalWebConfiguration extends WebSecurityConfigurerAdapter {

        private SessionRegistry sessionRegistry;

        @Autowired
        private MessageSource messageSource;
        
        @Autowired
        private RememberMeTokenRepository rememberMeTokenRepository;

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.sessionManagement()
                    .sessionAuthenticationStrategy(compositeSessionAuthenticationStrategy())
                    .sessionFixation()
                    .changeSessionId()
                    .maximumSessions(1)
                    .maxSessionsPreventsLogin(true)
                    .expiredUrl("/login?expired")
                    .sessionRegistry(sessionRegistry())
                    .and()
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .invalidSessionUrl("/")
                    .and()
                    .rememberMe()
                    .rememberMeParameter("remember-me")
                    .tokenRepository(persistentTokenRepository())
                    .tokenValiditySeconds(1209600);

            // Here we protect site from:
            // 1. X-Content-Type-Options
            http.headers().contentTypeOptions();
            // 2. Web Browser XSS Protection
            http.headers().xssProtection();
            http.headers().cacheControl();
            http.headers().httpStrictTransportSecurity();
            // 3. X-Frame-Options
            http.headers().frameOptions();
        }
        
        @Bean
	public PersistentTokenRepository persistentTokenRepository() {
            return new CustomPersistentTokenRepositoryImpl(rememberMeTokenRepository);
        }

        @Bean
        public SessionRegistry sessionRegistry() {
            if (sessionRegistry == null) {
                sessionRegistry = new SessionRegistryImpl();
            }
            return sessionRegistry;
        }

        @Bean
        @Order(1)
        public ConcurrentSessionControlAuthenticationStrategy concurrentSessionControlAuthenticationStrategy() {
            ConcurrentSessionControlAuthenticationStrategy strategy = new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry());
            strategy.setExceptionIfMaximumExceeded(true);
            strategy.setMessageSource(messageSource);
            return strategy;
        }
        
        @Bean
        @Order(2)
        public SessionFixationProtectionStrategy sessionFixationProtectionStrategy(){
            return new SessionFixationProtectionStrategy();
        }

        @Bean
        @Order(3)
        public RegisterSessionAuthenticationStrategy registerSessionAuthenticationStrategy(){
            RegisterSessionAuthenticationStrategy registerSessionAuthenticationStrategy = new RegisterSessionAuthenticationStrategy(sessionRegistry());
            return registerSessionAuthenticationStrategy;
        }
        
        @Bean
        public CompositeSessionAuthenticationStrategy compositeSessionAuthenticationStrategy(){
            List<SessionAuthenticationStrategy> sessionAuthenticationStrategies = new ArrayList<>();
            sessionAuthenticationStrategies.add(concurrentSessionControlAuthenticationStrategy());
            sessionAuthenticationStrategies.add(sessionFixationProtectionStrategy());
            sessionAuthenticationStrategies.add(registerSessionAuthenticationStrategy());
            CompositeSessionAuthenticationStrategy compositeSessionAuthenticationStrategy = new CompositeSessionAuthenticationStrategy(sessionAuthenticationStrategies);
            return compositeSessionAuthenticationStrategy;
        }

    }
}
