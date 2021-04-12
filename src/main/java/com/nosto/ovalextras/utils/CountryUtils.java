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

import ch.digitalfondue.vatchecker.EUVatCheckResponse;
import ch.digitalfondue.vatchecker.EUVatChecker;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CountryUtils {
    // Croatia 1st July 2013 onwards
    // UK left EU from 1st January 2021
    public static final ImmutableSet<String> EU_COUNTRIES =
            ImmutableSet.of("AT", "BE", "BG", "CY", "CZ", "DK", "EE", "FI", "FR", "DE", "GR", "HU", "IE", "IT",
                    "LV", "LT", "LU", "MT", "NL", "PL", "PT", "RO", "SK", "SI", "ES", "SE", "HR");

    /**
     * @see #VAT_ID_PREFIX_GREECE
     */
    private static final String COUNTRY_CODE_GREECE = "GR";
    /**
     * Full EU VAT ID starts with country code except in case of Greece.
     * Greece uses EL as VAT prefix instead of its country code GR.
     * https://en.wikipedia.org/wiki/VAT_identification_number
     * @see #COUNTRY_CODE_GREECE
     */
    private static final String VAT_ID_PREFIX_GREECE = "EL";

    private static final Map<String, String> COUNTRY_NAME_TO_CODE;
    private static final Map<String, String> COUNTRY_CODE_TO_NAME;
    /**
     * Amount of VAT that we invoice. currently we only invoice VAT from finnish at 24%
     */
    private static final ImmutableMap<String, BigDecimal> APPLIED_VAT =
            ImmutableMap.of("FI", new BigDecimal("0.24"));

    static {
        Map<String, String> countryNameToCode = new HashMap<>();
        Map<String, String> countryCodeToName = new HashMap<>();
        for (String countryCode : Locale.getISOCountries()) {
            Locale locale = new Locale("", countryCode);
            countryNameToCode.put(locale.getDisplayCountry(), countryCode);
            countryCodeToName.put(countryCode, locale.getDisplayCountry());
        }
        COUNTRY_NAME_TO_CODE = ImmutableMap.copyOf(countryNameToCode);
        COUNTRY_CODE_TO_NAME = ImmutableMap.copyOf(countryCodeToName);
    }

    private CountryUtils() {}

    public static boolean isEuCountry(@Nullable String country) {
        if (country == null) {
            return false;
        }
        return EU_COUNTRIES.contains(country.toUpperCase());
    }

    public static boolean isValidVatId(@Nullable String vat, String countryCode) {
        String vatPrefix = COUNTRY_CODE_GREECE.equals(countryCode) ? VAT_ID_PREFIX_GREECE : countryCode;
        if (StringUtils.startsWith(vat, vatPrefix)) {
            vat = StringUtils.removeStart(vat, vatPrefix);
        }
        return !isEuCountry(countryCode) || (vat != null && validate(countryCode, vat));
    }

    private static boolean validate(@Nullable String vat, String countryCode) {
        EUVatCheckResponse resp = EUVatChecker.doCheck(countryCode, vat);
        return resp.isValid();
    }
}
