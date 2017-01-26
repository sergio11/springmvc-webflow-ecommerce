package persistence.constraints;

import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ FIELD, METHOD, PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AfterTomorrowValidator.class)
@Documented
public @interface AfterTomorrow {
    String message() default "{AfterTomorrow.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
