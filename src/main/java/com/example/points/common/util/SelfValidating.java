package com.example.points.common.util;

import javax.validation.*;
import java.util.Set;

public interface SelfValidating<T> {

    default Validator getValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        return factory.getValidator();
    }

    default void validateSelf() {
        Validator validator = getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate((T) this);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

}
