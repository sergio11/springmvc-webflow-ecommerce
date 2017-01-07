package config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
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
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import persistence.models.AuthorityEnum;
import security.handlers.CustomLogoutHandler;

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
    
    
    @Configuration
    public class GlobalWebConfiguration extends WebSecurityConfigurerAdapter {

        private SessionRegistry sessionRegistry;

        @Autowired
        private MessageSource messageSource;

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.sessionManagement()
                    .sessionAuthenticationStrategy(concurrentSessionControlAuthenticationStrategy())
                    .sessionFixation()
                    .changeSessionId()
                    .maximumSessions(1)
                    .maxSessionsPreventsLogin(true)
                    .expiredUrl("/login?expired")
                    .sessionRegistry(sessionRegistry)
                    .and()
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .invalidSessionUrl("/");

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
        public SessionRegistry sessionRegistry() {
            if (sessionRegistry == null) {
                sessionRegistry = new SessionRegistryImpl();
            }
            return sessionRegistry;
        }

        @Bean
        public ConcurrentSessionControlAuthenticationStrategy concurrentSessionControlAuthenticationStrategy() {
            ConcurrentSessionControlAuthenticationStrategy strategy = new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry());
            strategy.setExceptionIfMaximumExceeded(true);
            strategy.setMessageSource(messageSource);
            return strategy;
        }
    }
    
    /**
     * 
     * Security Configuration for Admin zone
     */
    @Configuration
    @Order(1)
    public class AdminConfiguration extends WebSecurityConfigurerAdapter {
 
        @Autowired
        private AuthenticationSuccessHandler successHandler;
        
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
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
                .exceptionHandling().accessDeniedPage("/403")
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
                    .permitAll()
                .and()
                .logout()
                    .addLogoutHandler(logoutHandler)
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/login?logout")
                    .deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true)
                .and()
                .exceptionHandling().accessDeniedPage("/403")
                .and()
                .csrf();
        }
    }
}
