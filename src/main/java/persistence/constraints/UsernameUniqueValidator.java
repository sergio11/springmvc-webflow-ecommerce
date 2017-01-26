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
public class UsernameUniqueValidator implements ConstraintValidator<UsernameUnique, String> {
    
    private static Logger logger = LoggerFactory.getLogger(UsernameUniqueValidator.class);
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(UsernameUnique constraintAnnotation) {}

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        return userRepository.existsUserWithUsername(username) > 0 ? false : true;
    }
}
