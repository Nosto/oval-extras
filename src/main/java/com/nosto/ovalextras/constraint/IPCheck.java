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

import com.google.common.net.InetAddresses;
import net.sf.oval.ValidationCycle;
import net.sf.oval.configuration.annotation.AbstractAnnotationCheck;
import net.sf.oval.exception.OValException;

import java.util.HashSet;
import java.util.Set;

/**
 * Checks if the value given is a IP address
 *
 * <br>
 * <b>Note:</b> This constraint is also satisfied when the value to validate is null, therefore you might also need to specified @NotNull
 */
public class IPCheck extends AbstractAnnotationCheck<IP> {

    /**
     * Checks the all the addresses are valid IP addresses. It iterates over
     * each item in the set in uses the Google Commons validator to check them
     * @param value value
     * @return boolean
     */
    @SuppressWarnings({"UnstableApiUsage", "unchecked", "IfStatementWithIdenticalBranches"})
    public boolean isSatisfied(Object value) {
        if (value == null) {
            return true;
        } else {
            Set<String> ignoredAddresses = (HashSet<String>) value;
            for (String ignoredAddress : ignoredAddresses) {
                if (!InetAddresses.isInetAddress(ignoredAddress)) {
                    return false;
                }
            }
            return true;
        }
    }

    @Override
    public boolean isSatisfied(final Object validatedObject, final Object value, final ValidationCycle cycle) throws OValException {
        return isSatisfied(value);
    }
}
