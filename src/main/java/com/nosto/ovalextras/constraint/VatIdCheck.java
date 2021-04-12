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

import com.nosto.ovalextras.utils.CountryUtils;
import net.sf.oval.Validator;
import net.sf.oval.configuration.annotation.AbstractAnnotationCheck;
import net.sf.oval.context.OValContext;
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
    public boolean isSatisfied(Object validatedObject, @Nullable Object value, @Nullable OValContext context, @Nullable Validator validator) throws OValException {
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
}
