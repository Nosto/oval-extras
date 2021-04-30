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

import com.google.common.collect.Sets;
import net.sf.oval.ValidationCycle;
import net.sf.oval.configuration.annotation.AbstractAnnotationCheck;
import net.sf.oval.exception.OValException;

import java.util.Locale;
import java.util.Set;

public class CountryCheck extends AbstractAnnotationCheck<Country> {

    @Override
    public boolean isSatisfied(final Object validatedObject, final Object value, final ValidationCycle cycle) throws OValException {
        if (value == null) {
            return true;
        } else {
            Set<String> countries = Sets.newHashSet(Locale.getISOCountries());
            return countries.contains(value.toString().toUpperCase());
        }
    }
}
