package com.example.zcbeanvalidation.validation.utils;


import com.example.zcbeanvalidation.SpringBeanValidationDemo;
import com.example.zcbeanvalidation.ValidatorImpl;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Configuration
public class ValidationInterceptor {

    @Autowired
    ValidatorImpl validator;

    @Bean(name = "proxyValidationbean")
    public SpringBeanValidationDemo generateCGLibProxyBean() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(SpringBeanValidationDemo.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object source, Method method, Object[] args,
                                    MethodProxy methodProxy) throws Throwable {
                // 这里省略了对@Validated的注解的查找和判断，如果有这个注解则执行validator.validate() 验证
                // 另外需要说明的是在这里进行 bean 的属性验证 但是实际上因为程序依赖于spring环境，并且继承的类中已经使用了@Validated 来标注，则在执行这个方法之前，就已经执行了一遍java的validation了
                // 这里只是实例代码，理解bean的校验使用
                validator.validate(args[0]);
                return  methodProxy.invokeSuper(source,args);
            }
        });
        return (SpringBeanValidationDemo) enhancer.create();
    }

}
