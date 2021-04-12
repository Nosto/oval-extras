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

import com.google.common.collect.ImmutableList;
import net.sf.oval.Validator;
import net.sf.oval.configuration.annotation.AbstractAnnotationCheck;
import net.sf.oval.context.OValContext;
import net.sf.oval.exception.OValException;

import java.util.List;
import java.util.Locale;

/**
 * Custom validation class for Play that validates the languages
 *
 * @author mridang
 */
public class LanguageCheck extends AbstractAnnotationCheck<Language> {

    public static final String INVALID_LANGUAGE_CODE = "Language code is invalid. Use ISO 639 two letter codes such as 'us' or 'fr'";
    public static final String MESSAGE = "global.merchant.language.invalid";
    private static final List<String> LANGUAGES = ImmutableList.copyOf(Locale.getISOLanguages());

    public static boolean isValid(String value) {
        return LANGUAGES.contains(value);
    }

    @Override
    public boolean isSatisfied(Object object, Object value, OValContext context, Validator validator) throws OValException {
        if (value == null) {
            return true;
        } else {
            return isValid(value.toString());
        }
    }
}
