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
package com.epam.eco.kafkamanager.udmetrics.schedule;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.codahale.metrics.MetricRegistry;

import com.epam.eco.kafkamanager.udmetrics.autoconfigure.UDMetricManagerProperties;

/**
 * @author Andrei_Tytsik
 */
public class ScheduleCalculatedMetricExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleCalculatedMetricExecutor.class);

    @Autowired
    private MetricRegistry metricRegistry;

    @Autowired
    private UDMetricManagerProperties metricManagerProperties;

    private ScheduledExecutorService executorService;

    @PostConstruct
    private void init() {
        initExecutorService();
    }

    @PreDestroy
    private void destroy() {
        shutdownExecutorService();
    }

    private void initExecutorService() {
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(
                () ->  calculate(),
                metricManagerProperties.getCalculationIntervalInMs() / 2,
                metricManagerProperties.getCalculationIntervalInMs(),
                TimeUnit.MILLISECONDS);
    }

    private void shutdownExecutorService() {
        executorService.shutdownNow();
    }

    public void calculate() {
        metricRegistry.getMetrics().entrySet().forEach(entry -> {
            try {
                if (entry.getValue() instanceof ScheduleCalculatedMetric) {
                    ((ScheduleCalculatedMetric)entry.getValue()).calculateValue();
                }
            } catch (Exception ex) {
                LOGGER.warn(
                        String.format(
                                "Failed to calculate value for schedule-calculatable metric %s",
                                entry.getKey()),
                        ex);
            }
        });
    }

}
