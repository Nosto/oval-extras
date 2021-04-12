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
import net.sf.oval.exception.OValException;

import java.util.Map;

public class VelocityCheck extends AbstractAnnotationCheck<Velocity> {

    private String error;

    @Override
    public boolean isSatisfied(Object object, Object value, OValContext context, Validator validator) throws OValException {
        return value == null || value instanceof String && doIsSatisfied((String) value);
    }

    private boolean doIsSatisfied(String value) {
        return true;
//        try {
//            VelocityHelper.eval(new VelocityContext(), value, " ", VelocityHelper.getSilentEngine());
//            return true;
//        } catch (Exception e) {
//            this.error = StringEscapeUtils.escapeHtml4(e.getMessage()).replace("\n", "<br />");
//            return false;
//        } finally {
//            requireMessageVariablesRecreation();
//        }
    }

    @Override
    protected Map<String, String> createMessageVariables() {
        Map<String, String> messageVariables = Validator.getCollectionFactory().createMap(1);
        messageVariables.put("error", error);
        return messageVariables;
    }
}
