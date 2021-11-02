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

import java.util.Arrays;
import java.util.Map;

import net.sf.oval.ValidationCycle;
import net.sf.oval.Validator;
import net.sf.oval.configuration.annotation.AbstractAnnotationCheck;
import net.sf.oval.exception.OValException;

@SuppressWarnings("unused")
public class ContainsCheck extends AbstractAnnotationCheck<Contains> {

    @SuppressWarnings("NullAway")
    private String[] values;

    @Override
    public void configure(Contains annotation) {
        this.values = annotation.values();
    }

    @Override
    public boolean isSatisfied(final Object validatedObject, final Object value, final ValidationCycle cycle) throws OValException {
        return Arrays.stream(values).allMatch(value.toString()::contains);
    }

    @Override
    public Map<String, String> createMessageVariables() {
        Map<String, String> messageVariables = Validator.getCollectionFactory().createMap(1);
        messageVariables.put("values", Arrays.toString(values));
        return messageVariables;
    }
}
