/*******************************************************************************
 * Copyright (c) 2018 Nosto Solutions Ltd All Rights Reserved.
 * <p>
 * This software is the confidential and proprietary information of
 * Nosto Solutions Ltd ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the agreement you entered into with
 * Nosto Solutions Ltd.
 ******************************************************************************/
package com.nosto.ovalextras.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Custom Currency implementation that allows adding multiple currencies for same country.
 * @author oskar
 *
 */
@SuppressWarnings("checkstyle:useNostoCurrency")
@JsonSerialize(using = ToStringSerializer.class)
@JsonDeserialize(using = Currency.CurrencyJsonDeserializer.class)
public class Currency {

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

    @Override
    public String toString() {
        return currencyCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Currency currency = (Currency) o;
        return Objects.equal(currencyCode, currency.currencyCode);
    }

    @Override
    public int hashCode() {
        return currencyCode.hashCode();
    }

    static class CurrencyJsonDeserializer extends JsonDeserializer<Currency> {

        @Override
        public Currency deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            return Currency.getInstance(p.getText().trim());
        }
    }
}
