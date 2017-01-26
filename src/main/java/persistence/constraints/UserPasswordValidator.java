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
public class UserPasswordValidator implements ConstraintValidator<UserCurrentPassword, String> {
    
    private static Logger logger = LoggerFactory.getLogger(UserPasswordValidator.class);
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void initialize(UserCurrentPassword constraintAnnotation) {}

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if(password == null) return false;
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user != null){
            return passwordEncoder.matches(password, user.getPassword());
        }
        return false;
    }
}
