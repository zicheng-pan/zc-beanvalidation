package com.example.zcbeanvalidation;

import com.example.zcbeanvalidation.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

@SpringBootTest
class ZcBeanvalidationApplicationTests {

    @Autowired
    ValidatorImpl validator;

    @Autowired
    @Qualifier("rawvalidationbean")
    SpringBeanValidationDemo userProcessor;

    @Autowired
    @Qualifier("proxyValidationbean")
    SpringBeanValidationDemo proxyValidationbean;

    @Test
    void testValidator() {
        User user = new User();
        user.setAge(999);
        ValidationResult result = validator.validate(user);
        System.out.println(result.isHasErrors());
        System.out.println(result.getErrMsg());
    }

    @Test
    void testSpringvalidator() {
        userProcessor.process(new User());
    }

    @Test
    void testSpringvalidatorProxyBean() {
        User user = new User();
        user.setAge(12);
        proxyValidationbean.process(user);
    }
}
