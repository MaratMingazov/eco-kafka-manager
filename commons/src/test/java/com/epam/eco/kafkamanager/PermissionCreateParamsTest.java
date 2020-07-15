/*
 * Copyright 2020 EPAM Systems
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
package com.epam.eco.kafkamanager;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.acl.AclOperation;
import org.apache.kafka.common.acl.AclPermissionType;
import org.apache.kafka.common.resource.PatternType;
import org.apache.kafka.common.resource.ResourceType;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.epam.eco.kafkamanager.utils.TestObjectMapperSingleton;

/**
 * @author Andrei_Tytsik
 */
public class PermissionCreateParamsTest {

    @Test
    public void testSerializedToJsonAndBack() throws Exception {
        PermissionCreateParams origin = PermissionCreateParams.builder().
                resourceType(ResourceType.GROUP).
                resourceName("group").
                patternType(PatternType.LITERAL).
                principal("user:user").
                permissionType(AclPermissionType.ALLOW).
                operation(AclOperation.CREATE).
                host("host").
                build();

        ObjectMapper mapper = TestObjectMapperSingleton.getObjectMapper();

        String json = mapper.writeValueAsString(origin);
        Assert.assertNotNull(json);

        PermissionCreateParams deserialized = mapper.readValue(
                json,
                PermissionCreateParams.class);
        Assert.assertNotNull(deserialized);
        Assert.assertEquals(origin, deserialized);
    }

    @Test
    public void testDeserializedFromJson1() throws Exception {
        Map<String, Object> json = new HashMap<>();
        json.put("resourceType", "GROUP");
        json.put("resourceName", "group1");
        json.put("patternType", "LITERAL");
        json.put("principal", "user:user1");
        json.put("permissionType", "ALLOW");
        json.put("operation", "READ");
        json.put("host", "host");

        PermissionCreateParams params = PermissionCreateParams.fromJson(json);
        Assert.assertNotNull(params);
        Assert.assertEquals(ResourceType.GROUP, params.getResourceType());
        Assert.assertEquals("group1", params.getResourceName());
        Assert.assertEquals(PatternType.LITERAL, params.getPatternType());
        Assert.assertEquals("user:user1", params.getPrincipal());
        Assert.assertEquals(AclPermissionType.ALLOW, params.getPermissionType());
        Assert.assertEquals(AclOperation.READ, params.getOperation());
        Assert.assertEquals("host", params.getHost());
    }

    @Test
    public void testDeserializedFromJson2() throws Exception {
        String json =
                "{" +
                "\"resourceType\": \"GROUP\"" +
                ", \"resourceName\": \"group1\"" +
                ", \"patternType\": \"LITERAL\"" +
                ", \"principal\": \"user:user1\"" +
                ", \"permissionType\": \"ALLOW\"" +
                ", \"operation\": \"READ\"" +
                ", \"host\": \"host\"" +
                "}";

        PermissionCreateParams params = PermissionCreateParams.fromJson(json);
        Assert.assertNotNull(params);
        Assert.assertEquals(ResourceType.GROUP, params.getResourceType());
        Assert.assertEquals("group1", params.getResourceName());
        Assert.assertEquals(PatternType.LITERAL, params.getPatternType());
        Assert.assertEquals("user:user1", params.getPrincipal());
        Assert.assertEquals(AclPermissionType.ALLOW, params.getPermissionType());
        Assert.assertEquals(AclOperation.READ, params.getOperation());
        Assert.assertEquals("host", params.getHost());
    }

}
