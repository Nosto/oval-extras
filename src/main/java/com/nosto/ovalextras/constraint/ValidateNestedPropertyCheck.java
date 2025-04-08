/*
 *  Copyright (c) 2025 Nosto Solutions Ltd All Rights Reserved.
 *
 *  This software is the confidential and proprietary information of
 *  Nosto Solutions Ltd ("Confidential Information"). You shall not
 *  disclose such Confidential Information and shall use it only in
 *  accordance with the terms of the agreement you entered into with
 *  Nosto Solutions Ltd.
 */

package com.nosto.ovalextras.constraint;

import net.sf.oval.ConstraintViolation;
import net.sf.oval.ValidationCycle;
import net.sf.oval.Validator;
import net.sf.oval.configuration.annotation.AbstractAnnotationCheck;
import net.sf.oval.exception.OValException;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class ValidateNestedPropertyCheck extends AbstractAnnotationCheck<ValidateNestedProperty> {

    private static final long serialVersionUID = 1L;

    @Override
    @SuppressWarnings("java:S3516")
    public boolean isSatisfied(final Object validatedObject, final Object value, final ValidationCycle cycle) throws OValException {
        if (value != null) {
            Validator validator = Objects.requireNonNull(cycle).getValidator();

            if (value instanceof Collection<?>) {
                // valueToValidate is a collection
                Collection<?> col = (Collection<?>) value;
                for (Object object : col) {
                    List<ConstraintViolation> violations = validator.validate(object);
                    addViolations(cycle, violations);
                }

                return true;
            }

            if (value.getClass().isArray()) {
                // valueToValidate is an array
                int length = Array.getLength(value);
                for (int i = 0; i < length; i++) {
                    Object o = Array.get(value, i);
                    List<ConstraintViolation> violations = validator.validate(o);
                    addViolations(cycle, violations);
                }

                return true;
            }

            // valueToValidate is other object
            List<ConstraintViolation> violations = validator.validate(value);
            addViolations(cycle, violations);
        }

        return true;
    }

    private void addViolations(ValidationCycle validationCycle, List<ConstraintViolation> violations) {
        for (ConstraintViolation constraintViolation : violations) {
            validationCycle.addConstraintViolation(constraintViolation);
        }
    }
}
