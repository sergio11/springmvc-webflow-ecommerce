package persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import persistence.repositories.UserRepository;

/**
 * @author sergio
 */
public class EmailUniqueValidator implements ConstraintValidator<EmailUnique, String> {
    
    private static Logger logger = LoggerFactory.getLogger(EmailUniqueValidator.class);
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(EmailUnique constraintAnnotation) {}

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return userRepository.existsUserWithEmail(email) > 0 ? Boolean.FALSE : Boolean.TRUE;
    }
}
