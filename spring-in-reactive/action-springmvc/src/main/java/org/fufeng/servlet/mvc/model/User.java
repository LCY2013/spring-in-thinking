package org.fufeng.servlet.mvc.model;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Date;

@Data
public class User {
    @Email(message = "邮箱格式错误")
    private String email;

    @NotNull
    @Size(min = 2, max = 30, message = "名字长度应在2到30之间")
    private String name;

    @NotNull
    @Min(18)
    private Integer age;

    @NotNull
    @Past
    private Date birthday;
}
