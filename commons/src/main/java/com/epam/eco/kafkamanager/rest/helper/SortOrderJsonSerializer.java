/*
 * Copyright 2019 EPAM Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.epam.eco.kafkamanager.rest.helper;

import java.io.IOException;

import org.springframework.data.domain.Sort;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * @author Raman_Babich
 */
public class SortOrderJsonSerializer extends StdSerializer<Sort.Order> {

    private static final long serialVersionUID = 424975516356922899L;

    public SortOrderJsonSerializer() {
        super(Sort.Order.class);
    }

    @Override
    public void serialize(Sort.Order order, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField(SortOrderFields.PROPERTY, order.getProperty());
        gen.writeObjectField(SortOrderFields.DIRECTION, order.getDirection());
        gen.writeBooleanField(SortOrderFields.IGNORE_CASE, order.isIgnoreCase());
        gen.writeObjectField(SortOrderFields.NULL_HANDLING, order.getNullHandling());
        gen.writeEndObject();
    }
}
