// Copyright 2018 Oath Inc.
// Licensed under the terms of the Apache license. Please see LICENSE.md file distributed with this work for terms.
package com.yahoo.bard.webservice.data.dimension.datatype;

/**
 * Text dimension datatype.
 */
public class TextDimensionType implements DimensionDatatype {
    @Override
    public String getName() {
        return "Text";
    }

    @Override
    public boolean isA(DimensionDatatype otherType) {
        return otherType instanceof TextDimensionType;
    }
}
