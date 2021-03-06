/*
 * Copyright 2005-2021 by Sebastian Thomschke and contributors.
 * SPDX-License-Identifier: EPL-2.0
 */
package com.nosto.ovalextras.constraint;

import static org.assertj.core.api.Assertions.assertThat;

import net.sf.oval.Check;

/**
 * @author Sebastian Thomschke
 */
public abstract class AbstractContraintsTest {

    /**
     * Performs basic tests of the check implementation.
     */
    protected void testCheck(final Check check) {
        check.setMessage("XYZ");
        assertThat(check.getMessage()).isEqualTo("XYZ");

        check.setProfiles("p1");
        assertThat(check.getProfiles()).isNotNull();
        assertThat(check.getProfiles()).hasSize(1);
        assertThat(check.getProfiles()[0]).isEqualTo("p1");

        check.setProfiles((String[]) null);
        assertThat(check.getProfiles() == null || check.getProfiles().length == 0).isTrue();
    }
}