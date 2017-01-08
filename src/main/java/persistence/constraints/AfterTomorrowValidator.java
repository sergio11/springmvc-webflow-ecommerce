package persistence.constraints;

import java.util.Calendar;
import java.util.Date;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author sergio
 */
public class AfterTomorrowValidator implements ConstraintValidator<AfterTomorrow, Date> {
    
    public final void initialize(final AfterTomorrow annotation) {}

    public final boolean isValid(final Date value, final ConstraintValidatorContext context) {
        Calendar c = Calendar.getInstance(); 
        c.setTime(value); 
        c.add(Calendar.DATE, 1);
        return value.after(c.getTime());
    }
}
