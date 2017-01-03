package persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import persistence.models.User;

/**
 * @author sergio
 */
public class CurrentUserPasswordValidator implements ConstraintValidator<UserCurrentPassword, String> {
    
    private static Logger logger = LoggerFactory.getLogger(CurrentUserPasswordValidator.class);
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void initialize(UserCurrentPassword constraintAnnotation) {}

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user != null){
            logger.info(user.toString());
            return passwordEncoder.encode(password) == user.getPassword();
        }
        return false;
    }
}
