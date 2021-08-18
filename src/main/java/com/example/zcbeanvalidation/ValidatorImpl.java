package com.example.zcbeanvalidation;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

@Component
public class ValidatorImpl implements InitializingBean {

    private Validator validator;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }


    public ValidationResult validate(Object obj) {
        System.out.println("validate bean"+obj.getClass().getName());
        ValidationResult result = new ValidationResult();
        Set<ConstraintViolation<Object>> set = validator.validate(obj);
        if (set.size() > 0) {
            result.setHasErrors(true);
            set.forEach(item -> {
                String errorMessage = item.getMessage();
                String propertyName = item.getPropertyPath().toString();
                result.getErrMsgMap().put(propertyName, errorMessage);
            });
        }
        return result;
    }
}
