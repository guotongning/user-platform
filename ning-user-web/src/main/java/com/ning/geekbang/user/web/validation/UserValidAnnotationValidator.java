package com.ning.geekbang.user.web.validation;


import com.ning.geekbang.user.web.domain.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class UserValidAnnotationValidator implements ConstraintValidator<UserValid, User> {

    private int idRange;

    public void initialize(UserValid annotation) {
        this.idRange = annotation.idRange();
    }

    @Override
    public boolean isValid(User value, ConstraintValidatorContext context) {
        String template = context.getDefaultConstraintMessageTemplate();
        //TODO 待完成
        return false;
    }
}
