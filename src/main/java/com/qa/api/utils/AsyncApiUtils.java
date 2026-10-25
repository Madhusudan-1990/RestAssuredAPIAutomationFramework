package com.qa.api.utils;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import com.qa.api.client.RestClient;
import com.qa.api.constants.AuthType;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

/**
 * Reusable helper for polling APIs whose processing continues asynchronously.
 *
 * Example: a workflow returns status 202 when accepted and later reaches
 * a business status such as 6090 when complete.
 */
public class AsyncApiUtils {

    private final RestClient restClient;

    public AsyncApiUtils(RestClient restClient) {
        this.restClient = restClient;
    }

    /**
     * Polls a status endpoint until the expected status is observed or timeout.
     *
     * @param baseUrl API base URL
     * @param endpoint status endpoint, for example "/wave-runs/{waveRunId}"
     * @param waveRunId ID used to track the asynchronous run
     * @param statusJsonPath JSON path to the status field, for example "status"
     * @param expectedStatus status that means the workflow is complete
     * @param timeoutSeconds maximum overall wait time
     * @param pollIntervalMillis delay between checks
     * @param authType authentication configured by the framework
     * @return the final observed status
     */
    public int waitForStatus(
            String baseUrl,
            String endpoint,
            String waveRunId,
            String statusJsonPath,
            int expectedStatus,
            int timeoutSeconds,
            long pollIntervalMillis,
            AuthType authType) {

        long deadline = System.nanoTime()
                + TimeUnit.SECONDS.toNanos(timeoutSeconds);

        int currentStatus = -1;

        while (System.nanoTime() < deadline) {
            Response response = restClient.get(
                    baseUrl,
                    endpoint,
                    Collections.<String, String>emptyMap(),
                    Collections.singletonMap("waveRunId", waveRunId),
                    authType,
                    ContentType.JSON);

            currentStatus = response.jsonPath().getInt(statusJsonPath);

            if (currentStatus == expectedStatus) {
                return currentStatus;
            }

            try {
                Thread.sleep(pollIntervalMillis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException(
                        "Polling was interrupted for wave run " + waveRunId, e);
            }
        }

        throw new AssertionError(
                "Timed out waiting for wave run " + waveRunId
                + ". Expected status: " + expectedStatus
                + ", last observed status: " + currentStatus);
    }
}
