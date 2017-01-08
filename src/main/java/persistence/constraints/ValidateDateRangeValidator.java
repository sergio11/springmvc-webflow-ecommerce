package persistence.constraints;

import java.util.Date;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.beanutils.PropertyUtils;

/**
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
            final Date start = (Date)PropertyUtils.getProperty(value, startFieldName);
            final Date end = (Date)PropertyUtils.getProperty(value, endFieldName);
            return start == null && end == null || start != null && end == null || start != null && end.after(start);
        }
        catch (final Exception ignore)
        {
            // ignore
        }
        return true;
    }
    
}
