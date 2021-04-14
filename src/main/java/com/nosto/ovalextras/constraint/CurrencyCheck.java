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
import net.sf.oval.exception.OValException;

/**
 * Custom validation class for Play that validates the currencies
 *
 * @author mridang
 */
public class CurrencyCheck extends AbstractAnnotationCheck<Currency> {

    public static final String MESSAGE = "global.merchant.currency.invalid";

    public static boolean isValidCurrency(Object value) {
        try {
            getCurrency(value.toString());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public boolean isSatisfied(Object object, Object value, OValContext context, Validator validator) throws OValException {
        if (value == null) {
            return true;
        } else {
            return isValidCurrency(value);
        }
    }

    private static java.util.Currency getCurrency(String code) {
        return java.util.Currency.getInstance(code.trim().toUpperCase());
    }
}
