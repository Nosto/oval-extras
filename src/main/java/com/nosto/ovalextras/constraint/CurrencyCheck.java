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

import com.google.common.base.Preconditions;
import net.sf.oval.Validator;
import net.sf.oval.configuration.annotation.AbstractAnnotationCheck;
import net.sf.oval.context.OValContext;
import net.sf.oval.exception.OValException;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Custom validation class for Play that validates the currencies
 *
 * @author mridang
 */
public class CurrencyCheck extends AbstractAnnotationCheck<Currency> {

    public static final String MESSAGE = "global.merchant.currency.invalid";

    public static boolean isValidCurrency(Object value) {
        try {
            Currency.getInstance(value.toString());
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

    private static class Currency {

        /**
         * The Iranian Rial is divided into 10 rials to a toman (IRT)
         */
        public static final Currency IRT;
        public static final Currency BYN;
        public static final Currency BYR;
        private static final ConcurrentMap<String, Currency> INSTANCES = new ConcurrentHashMap<>(7);
        private final String currencyCode;
        private final String symbol;

        private final int defaultFractionDigits;

        static {
            IRT = new Currency("IRT", "تومان", Math.max(0, Currency.getInstance("IRR").getDefaultFractionDigits() - 1));
            BYN = new Currency("BYN", "Br", 2);
            BYR = new Currency("BYR", "p.", 2);
            INSTANCES.put("IRT", IRT);
            INSTANCES.put("BYN", BYN);
            INSTANCES.put("BYR", BYR);
            INSTANCES.put("РУБ", Currency.getInstance("RUB"));
            INSTANCES.put("KR", Currency.getInstance("SEK"));
            INSTANCES.put("€", Currency.getInstance("EUR"));
        }

        private Currency(String currencyCode,
                         String symbol,
                         int defaultFractionDigits) {
            this.currencyCode = Preconditions.checkNotNull(currencyCode, "currency code has to be set");
            this.symbol = symbol;
            this.defaultFractionDigits = defaultFractionDigits;
        }

        private Currency(java.util.Currency currency) {
            this(currency.getCurrencyCode(), currency.getSymbol(), currency.getDefaultFractionDigits());
        }

        public static Currency getInstance(String code) {
            return INSTANCES.computeIfAbsent(code.trim().toUpperCase(), c -> new Currency(java.util.Currency.getInstance(c)));
        }

        public int getDefaultFractionDigits() {
            return defaultFractionDigits;
        }

    }
}
