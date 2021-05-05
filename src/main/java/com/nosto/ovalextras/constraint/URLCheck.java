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

import net.sf.oval.ValidationCycle;
import net.sf.oval.configuration.annotation.AbstractAnnotationCheck;
import net.sf.oval.exception.OValException;
import okhttp3.HttpUrl;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;

import java.util.regex.Pattern;

public class URLCheck extends AbstractAnnotationCheck<URL> {

    public final static String ALLOW_LOCAL = "oval.url.allow.local";

    private static final UrlValidator VALIDATOR = new UrlValidator((Boolean.parseBoolean(System.getProperty(ALLOW_LOCAL)) ? UrlValidator.ALLOW_LOCAL_URLS : 0) + UrlValidator.ALLOW_2_SLASHES) {
        @Override
        protected boolean isValidAuthority(String authority) {
            if (authority != null) {
                authority = authority.replace("_", "-");
            }
            return super.isValidAuthority(authority);
        }
    };

    private static final Pattern DOUBLE_SLASH = Pattern.compile("([^:])//");

    @Override
    public boolean isSatisfied(final Object validatedObject, final Object value, final ValidationCycle cycle) throws OValException {
        if (value == null) {
            return true;
        } else {
            String url = (String) value;
            if (StringUtils.isBlank(url)) {
                return true;
            } else {
                return isValidUrl(url);
            }
        }
    }

    public  boolean isValidUrl(String url) {
        try {
            if (url.startsWith("//")) {
                url = "http:" + url;
            } else if (url.startsWith("://")) {
                url = "http" + url;
            }

            HttpUrl httpUrl = HttpUrl.parse(url);
            return httpUrl != null && VALIDATOR.isValid(removeDoubleSlashesFromPath(httpUrl.uri().toString()));
        } catch (Exception e) {
            return false;
        }
    }

    private static String removeDoubleSlashesFromPath(String url) {
        return DOUBLE_SLASH.matcher(url).replaceAll("$1/");
    }

}
