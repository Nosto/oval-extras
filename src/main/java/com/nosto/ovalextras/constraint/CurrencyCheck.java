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

import javax.annotation.Nullable;

import net.sf.oval.ValidationCycle;
import net.sf.oval.configuration.annotation.AbstractAnnotationCheck;
import net.sf.oval.exception.OValException;

import java.util.Locale;

public class CurrencyCheck extends AbstractAnnotationCheck<Currency> {

    public static boolean isValidCurrency(Object value) {
        try {
            java.util.Currency.getInstance(value.toString().trim().toUpperCase(Locale.ROOT));
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public boolean isSatisfied(final Object validatedObject, final Object value, @Nullable final ValidationCycle cycle) throws OValException {
        if (value == null) {
            return true;
        } else {
            return isValidCurrency(value);
        }
    }

}
