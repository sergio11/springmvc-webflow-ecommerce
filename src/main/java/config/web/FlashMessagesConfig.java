package config.web;

import es.sandbox.ui.messages.CssClassesByLevel;
import es.sandbox.ui.messages.Level;
import es.sandbox.ui.messages.spring.config.annotation.EnableFlashMessages;
import es.sandbox.ui.messages.spring.config.annotation.FlashMessagesConfigurerAdapter;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFlashMessages
public class FlashMessagesConfig extends FlashMessagesConfigurerAdapter {

    @Override
    public void configureCssClassesByLevel(CssClassesByLevel cssClasses) {
        cssClasses.put(Level.ERROR, "alert alert-danger");
        cssClasses.put(Level.INFO, "alert alert-info");
        cssClasses.put(Level.SUCCESS, "alert alert-success");
        cssClasses.put(Level.WARNING, "alert alert-warning");
    }
}
