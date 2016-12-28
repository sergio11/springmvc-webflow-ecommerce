package persistence.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author sergio
 */
public class ValidateDateRangeValidator implements ConstraintValidator<ValidateDateRange, Object>{
    
    private String startFieldName;
    private String endFieldName;

    @Override
    public void initialize(ValidateDateRange constraintAnnotation) {
        startFieldName = constraintAnnotation.start();
        endFieldName = constraintAnnotation.end();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try
        {
            final Long start = Long.valueOf(BeanUtils.getProperty(value, startFieldName));
            final Long end = Long.valueOf(BeanUtils.getProperty(value, endFieldName));
            return start == null && end == null || start != null && end > start;
        }
        catch (final Exception ignore)
        {
            // ignore
        }
        return true;
    }
    
}
