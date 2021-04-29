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

import com.nosto.ovalextras.utils.URIUtil;
import net.sf.oval.ValidationCycle;
import net.sf.oval.configuration.annotation.AbstractAnnotationCheck;
import net.sf.oval.exception.OValException;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

/**
 * Checks if the value given is a correct domain, or list of domains
 *
 * <br>
 * <b>Note:</b> This constraint is also satisfied when the value to validate is null, therefore you might also need to specified @NotNull
 */
public class DomainCheck extends AbstractAnnotationCheck<Domain> {

    @SuppressWarnings({"unchecked"})
    private static boolean isValidDomain(Object value) {
        if (value instanceof String) {
            String subDomain = (String) value;
            return StringUtils.isBlank(subDomain) || URIUtil.isValidDomain(subDomain);
        } else if (value instanceof Collection) {
            Collection<String> col = (Collection<String>) value;
            return col.stream().allMatch(URIUtil::isValidDomain);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public boolean isSatisfied(final Object validatedObject, final Object value, final ValidationCycle cycle) throws OValException {
        if (value == null) {
            return true;
        } else {
            return isValidDomain(value);
        }
    }
}
