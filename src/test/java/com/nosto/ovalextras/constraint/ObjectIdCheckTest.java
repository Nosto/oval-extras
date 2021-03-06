/*
 *  Copyright (c) 2021 Nosto Solutions Ltd All Rights Reserved.
 *
 *  This software is the confidential and proprietary information of
 *  Nosto Solutions Ltd ("Confidential Information"). You shall not
 *  disclose such Confidential Information and shall use it only in
 *  accordance with the terms of the agreement you entered into with
 *  Nosto Solutions Ltd.
 */

package com.nosto.ovalextras.constraint;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.bson.types.ObjectId;
import org.junit.Test;

public class ObjectIdCheckTest extends AbstractContraintsTest {

    @Test
    public void testValidObjectId() {
        ObjectIdCheck objectIdCheck = new ObjectIdCheck();
        super.testCheck(objectIdCheck);

        assertTrue(objectIdCheck.isSatisfied(new Object(), ObjectId.get(), null));
    }

    @Test
    public void testInvalidObjectId() {
        ObjectIdCheck objectIdCheck = new ObjectIdCheck();
        super.testCheck(objectIdCheck);

        assertFalse(objectIdCheck.isSatisfied(new Object(), "12324", null));
        assertFalse(objectIdCheck.isSatisfied(new Object(), "", null));
        assertFalse(objectIdCheck.isSatisfied(new Object(), "someObjectId", null));
    }
}
