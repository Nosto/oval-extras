/*******************************************************************************
 * Copyright (c) 2018 Nosto Solutions Ltd All Rights Reserved.
 * <p>
 * This software is the confidential and proprietary information of
 * Nosto Solutions Ltd ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the agreement you entered into with
 * Nosto Solutions Ltd.
 ******************************************************************************/
package com.nosto.ovalextras.constraint;

import net.sf.oval.Validator;
import net.sf.oval.configuration.annotation.AbstractAnnotationCheck;
import net.sf.oval.context.OValContext;

import java.util.Collection;

@SuppressWarnings("serial")
public class RequiredCheck extends AbstractAnnotationCheck<Required> {

    @Override
    public boolean isSatisfied(Object validatedObject, Object value, OValContext context, Validator validator) {
        if (value == null) {
            return false;
        }
        if (value instanceof String) {
            return value.toString().trim().length() > 0;
        }
        if (value instanceof Collection<?>) {
            return ((Collection<?>) value).size() > 0;
        }
        if (value.getClass().isArray()) {
            try {
                return java.lang.reflect.Array.getLength(value) > 0;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }
}
