/*******************************************************************************
 * Copyright (c) 2018 Nosto Solutions Ltd All Rights Reserved.
 * <p>
 * This software is the confidential and proprietary information of
 * Nosto Solutions Ltd ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the agreement you entered into with
 * Nosto Solutions Ltd.
 ******************************************************************************/
package com.nosto.ovalextras.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.DomainValidator;

import javax.annotation.Nullable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

/**
 * @author stephenfenech
 *
 */
public final class URIUtil {

    private URIUtil() {}

    /**
     *
     * @param uri
     * @return domain name without the subdomain 'www'
     */
    @Nullable
    public static String getDomainName(String uri) {
        if (uri == null) {
            return null;
        }
        try {
            if (uri.startsWith("//")) {
                uri = "https:" + uri;
            }
            URL theUri = new URL(uri);
            String host = theUri.getHost().toLowerCase();
            return removeWWWSubDomain(host);
        } catch (MalformedURLException e) {
            return null;
        }
    }

    public static boolean isValidDomain(String domain) {
        if (StringUtils.isNotBlank(domain)) {
            String domainToBeChecked = domain.endsWith("/") ? domain.substring(0, domain.length() - 1) : domain;
            boolean passJavaUrlSpec = Objects.equals(domainToBeChecked, URIUtil.getDomainName("https://" + domain + "?test"));
            boolean passCommonsCheck = DomainValidator.getInstance().isValid(domainToBeChecked);
            return passJavaUrlSpec && passCommonsCheck;
        }

        return false;
    }

    @Nullable
    public static String removeWWWSubDomain(@Nullable String host) {
        return host != null && host.startsWith("www.") ? host.substring(4) : host;
    }
}


