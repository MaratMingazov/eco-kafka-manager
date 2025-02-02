/*******************************************************************************
 *  Copyright 2022 EPAM Systems
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License.  You may obtain a copy
 *  of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 *******************************************************************************/
package com.epam.eco.kafkamanager;

import org.apache.kafka.common.acl.AclOperation;
import org.apache.kafka.common.acl.AclPermissionType;
import org.apache.kafka.common.resource.PatternType;
import org.apache.kafka.common.resource.ResourceType;
import org.apache.kafka.common.security.auth.KafkaPrincipal;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.epam.eco.kafkamanager.utils.TestObjectMapperSingleton;

/**
 * @author Raman_Babich
 */
public class PermissionInfoTest {

    @Test
    public void testSerializedToJsonAndBack() throws Exception {
        PermissionInfo origin = PermissionInfo.builder()
                .resourceType(ResourceType.TOPIC)
                .resourceName("topicName")
                .patternType(PatternType.LITERAL)
                .kafkaPrincipal(new KafkaPrincipal("User", "John_Doe@acme.com"))
                .host("*")
                .operation(AclOperation.DESCRIBE)
                .permissionType(AclPermissionType.ALLOW)
                .metadata(Metadata.builder()
                        .description("description")
                        .attribute("a", "a")
                        .updatedBy("me")
                        .updatedAtNow()
                        .build())
                .build();

        ObjectMapper mapper = TestObjectMapperSingleton.getObjectMapper();

        String json = mapper.writeValueAsString(origin);
        Assert.assertNotNull(json);

        PermissionInfo deserialized = mapper.readValue(
                json,
                PermissionInfo.class);
        Assert.assertNotNull(deserialized);
        Assert.assertEquals(origin, deserialized);
    }

}
