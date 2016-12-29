package persistence.constraints;

import javax.validation.Constraint;
import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.validation.Payload;
/**
 *
 * @author sergio
 */
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = ValidateDateRangeValidator.class)
@Documented
public @interface ValidateDateRange {
    String message() default "{constraints.validateDateRange}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    /**
     * @return The Start Date
     */
    String start();
    /**
     * @return The second field
     */
    String end();
}
