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

import net.sf.oval.configuration.annotation.Constraint;

import java.lang.annotation.*;

/**
 * Checks if the value given is a correct EU VAT format
 * Returns true for non-EU vat codes
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE, ElementType.TYPE_USE, ElementType.METHOD})
@Constraint(checkWith = VatIdCheck.class)
public @interface VatId {}
