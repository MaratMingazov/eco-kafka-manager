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

import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;

import com.epam.eco.kafkamanager.repo.KeyValueRepo;

/**
 * @author Andrei_Tytsik
 */
public interface TopicRepo extends KeyValueRepo<String, TopicInfo, TopicSearchCriteria> {

    @PreAuthorize("@authorizer.isPermitted('TOPIC', #topicName, 'CREATE')")
    TopicInfo create(
            String topicName,
            int partitionCount,
            int replicationFactor,
            Map<String, String> config);

    @PreAuthorize("@authorizer.isPermitted('TOPIC', #topicName, 'ALTER_CONFIG')")
    TopicInfo updateConfig(String topicName, Map<String, String> configs);

    @PreAuthorize("@authorizer.isPermitted('TOPIC', #topicName, 'ALTER')")
    TopicInfo createPartitions(String topicName, int newPartitionCount);

    @PreAuthorize("@authorizer.isPermitted('TOPIC', #topicName, 'DELETE')")
    void delete(String topicName);

}
