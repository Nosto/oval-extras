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

import com.nosto.ovalextras.utils.URLUtils;
import net.sf.oval.ValidationCycle;
import net.sf.oval.configuration.annotation.AbstractAnnotationCheck;
import net.sf.oval.exception.OValException;
import org.apache.commons.lang3.StringUtils;

public class URLCheck extends AbstractAnnotationCheck<URL> {

    public boolean isSatisfied(Object object, Object value) throws OValException {
        String url = (String) value;
        if (StringUtils.isBlank(url)) {
            return true;
        } else {
            return URLUtils.isValid(url);
        }
    }

    @Override
    public boolean isSatisfied(final Object validatedObject, final Object value, final ValidationCycle cycle) throws OValException {
        return isSatisfied(validatedObject, value);
    }
}
