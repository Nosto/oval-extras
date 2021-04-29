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

import net.sf.oval.ValidationCycle;
import net.sf.oval.configuration.annotation.AbstractAnnotationCheck;
import net.sf.oval.exception.OValException;

import java.util.regex.Pattern;

/**
 * Checks if the value given is a correct phone code format
 *
 * <br>
 * <b>Note:</b> This constraint is also satisfied when the value to validate is null, therefore you might also need to specified @NotNull
 */
public class PhoneCheck extends AbstractAnnotationCheck<Phone> {

    private static final Pattern PATTERN = Pattern.compile("^([+][0-9]{1,3}([ .\\-]))?([(]{1}[0-9]{1,6}[)])?([0-9 .\\-/]{3,20})((x|ext|extension)[ ]?[0-9]{1,4})?$");

    @Override
    public boolean isSatisfied(final Object validatedObject, final Object value, final ValidationCycle cycle) throws OValException {
        if (value == null || value.toString().length() == 0) {
            return true;
        }
        return PATTERN.matcher(value.toString()).matches();
    }
}
