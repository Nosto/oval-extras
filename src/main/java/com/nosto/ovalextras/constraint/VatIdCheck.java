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

import ch.digitalfondue.vatchecker.EUVatCheckResponse;
import ch.digitalfondue.vatchecker.EUVatChecker;
import com.google.common.collect.ImmutableSet;
import net.sf.oval.ValidationCycle;
import net.sf.oval.Validator;
import net.sf.oval.configuration.annotation.AbstractAnnotationCheck;
import net.sf.oval.exception.OValException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * Play validation annotation to verify that VAT ID is mandatory in EU countries.
 * 
 * @see VatId
 */
public class VatIdCheck extends AbstractAnnotationCheck<VatId> {

    private VatId vat;
    private String country;

    @Override
    public void configure(VatId vat) {
        this.vat = vat;
    }

    @Override
    public boolean isSatisfied(Object validatedObject, @Nullable Object value, @Nullable ValidationCycle validator) throws OValException {
        String countryCode = getCountryCode(validatedObject);
        String vat = (String) value;
        return getIgnoreValidation(validatedObject) || !CountryUtils.isEuCountry(countryCode) || isValid(vat, countryCode);
    }

    private boolean isValid(String vat, String countryCode) {
        if (StringUtils.isBlank(vat)) {
            setMessage("com.nosto.validation.constraint.VatId.required");
            return false;
        } else if (!CountryUtils.isValidVatId(vat, countryCode)) {
            this.country = new java.util.Locale("", countryCode).getDisplayCountry();
            requireMessageVariablesRecreation();
            setMessage("com.nosto.validation.constraint.VatId.violated");
            return false;
        }
        return true;
    }

    private String getCountryCode(Object validatedObject) {
        try {
            return (String) FieldUtils.readDeclaredField(validatedObject, vat.countryCodeField(), true);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to read country code field from validated object.", e);
        }
    }

    private boolean getIgnoreValidation(Object validatedObject) {
        if (StringUtils.isBlank(vat.ignoreValidationField())) {
            // If no vat field is not defined in annotation then this method returns false to indicate that there is no field to explicitly disable vat check 
            return false;
        }
        try {
            return (boolean) FieldUtils.readDeclaredField(validatedObject, vat.ignoreValidationField(), true);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to read no ignore validation field from validated object.", e);
        }
    }

    @Override
    protected Map<String, String> createMessageVariables() {
        Map<String, String> messageVariables = Validator.getCollectionFactory().createMap(1);
        messageVariables.put("country", country);
        return messageVariables;
    }

    private static class CountryUtils {
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

        @SuppressWarnings({"BooleanMethodIsAlwaysInverted"})
        public static boolean isEuCountry(@Nullable String country) {
            if (country == null) {
                return false;
            }
            return EU_COUNTRIES.contains(country.toUpperCase());
        }

        public static boolean isValidVatId(String vat, String countryCode) {
            String vatPrefix = COUNTRY_CODE_GREECE.equals(countryCode) ? VAT_ID_PREFIX_GREECE : countryCode;
            if (StringUtils.startsWith(vat, vatPrefix)) {
                vat = StringUtils.removeStart(vat, vatPrefix);
            }
            return !isEuCountry(countryCode) || (vat != null && validate(vat, countryCode));
        }

        private static boolean validate(@Nullable String vat, String countryCode) {
            EUVatCheckResponse resp = EUVatChecker.doCheck(countryCode, vat);
            return resp.isValid();
        }
    }

}
