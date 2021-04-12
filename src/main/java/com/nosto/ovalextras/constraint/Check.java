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

public abstract class Check {

    public CheckWithCheck checkWithCheck;

    public Check() {
    }

    public abstract boolean isSatisfied(Object validatedObject, Object value);

    /**
     * Set validation error message.
     * 
     * Correct format for variable placeholders is {var0}, {var1}, ...
     * instead of standard Java String formatting placeholders.
     * 
     * @param message validation error message template
     * @param vars variables filled into message template
     */
    public void setMessage(String message, String... vars) {
        checkWithCheck.setMessage(message);
        checkWithCheck.clearVariables();
        for (int i = 0; i < vars.length; i++) {
            checkWithCheck.putVariable("var" + i, vars[i]);
        }
        checkWithCheck.setVariables();
    }
}
