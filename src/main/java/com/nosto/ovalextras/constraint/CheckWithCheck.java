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

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.TreeMap;

@SuppressWarnings("serial")
public class CheckWithCheck extends AbstractAnnotationCheck<CheckWith> {

    static final String MES = "validation.invalid";
    private Map<String, String> variables = new TreeMap<>();
    private Check check;

    @Override
    public void configure(CheckWith checkWith) {
        setMessage(checkWith.message());
        try {
            Constructor<?> constructor = checkWith.value().getDeclaredConstructor();
            constructor.setAccessible(true);
            check = (Check) constructor.newInstance();
            check.checkWithCheck = this;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void clearVariables() {
        variables = new TreeMap<>();
    }

    protected void putVariable(String s, String var) {
        variables.put(s, var);
    }

    @Override
    protected Map<String, String> createMessageVariables() {
        return variables;
    }

    @Override
    public boolean isSatisfied(Object validatedObject, Object value, OValContext context, Validator validator) {
        return check.isSatisfied(validatedObject, value);
    }

    public void setVariables() {
        requireMessageVariablesRecreation();
    }
}