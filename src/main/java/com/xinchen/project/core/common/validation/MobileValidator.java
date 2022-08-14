package com.xinchen.project.core.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2022/8/14 17:06
 */
public class MobileValidator implements ConstraintValidator<Mobile,String> {
    private boolean required = false;
    /** 验证手机号 */
    private static final Pattern pattern = Pattern.compile("^1[34578][0-9]{9}$");

    @Override
    public void initialize(Mobile constraintAnnotation) {
        this.required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String mobile, ConstraintValidatorContext constraintValidatorContext) {
        return required && isMobile(mobile);
    }

    private boolean isMobile(String mobile) {
        return pattern.matcher(null == mobile ? "" : mobile).matches();
    }
}
