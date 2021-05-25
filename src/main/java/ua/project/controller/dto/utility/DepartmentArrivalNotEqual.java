package ua.project.controller.dto.utility;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE,ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = DepartmentArrivalNotEqualValidator.class)
@Documented
public @interface DepartmentArrivalNotEqual {
    String message() default "Arrival and department are equal";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
