package com.qa.api.utils;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import com.qa.api.client.RestClient;
import com.qa.api.constants.AuthType;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

/**
 * Reusable polling helper for asynchronous API workflows.
 */
public class AsyncApiUtils {

    private final RestClient restClient;

    public AsyncApiUtils(RestClient restClient) {
        this.restClient = restClient;
    }

    /**
     * Polls the status endpoint until the expected status is reached or timeout.
     *
     * Example endpoint: "/wave-runs/{waveRunId}"
     * Example status JSON path: "status"
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

            // The existing RestClient allows GET responses of 200 or 404.
            // A 404 can be temporary while an async resource becomes available.
            if (response.getStatusCode() == 200) {
                currentStatus = response.jsonPath().getInt(statusJsonPath);

                if (currentStatus == expectedStatus) {
                    return currentStatus;
                }
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
