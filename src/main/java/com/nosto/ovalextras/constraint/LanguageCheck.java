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
import net.sf.oval.ValidationCycle;
import net.sf.oval.configuration.annotation.AbstractAnnotationCheck;
import net.sf.oval.exception.OValException;

import java.util.Locale;

/**
 * Custom validation class for Play that validates the languages
 *
 * @author mridang
 */
public class LanguageCheck extends AbstractAnnotationCheck<Language> {

    private static final ImmutableList<String> LANGUAGES = ImmutableList.copyOf(Locale.getISOLanguages());

    public static boolean isValid(String value) {
        return LANGUAGES.contains(value);
    }

    @Override
    public boolean isSatisfied(final Object validatedObject, final Object value, final ValidationCycle cycle) throws OValException {
        if (value == null) {
            return true;
        } else {
            return isValid(value.toString());
        }
    }
}
