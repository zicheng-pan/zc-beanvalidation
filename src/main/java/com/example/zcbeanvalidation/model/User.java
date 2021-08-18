package com.example.zcbeanvalidation.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class User {

    @NotNull(message = "年龄不能为空")
    @Min(value = 0, message = "年龄必须大于零")
    @Max(value = 150, message = "年龄过大")
    private Integer age;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
