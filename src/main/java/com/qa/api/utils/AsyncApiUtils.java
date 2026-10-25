package com.qa.api.utils;

import java.util.Collections;

import com.qa.api.client.RestClient;
import com.qa.api.constants.AuthType;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

/**
 * Simple reusable polling utility for asynchronous API workflows.
 */
public class AsyncApiUtils {

    private final RestClient restClient;

    public AsyncApiUtils(RestClient restClient) {
        this.restClient = restClient;
    }

    public int waitForWaveRunCompletion(String baseUrl,
                                        String endpoint,
                                        String waveRunId) {
        int status = -1;

        // Check up to 30 times, waiting 2 seconds between checks.
        for (int attempt = 0; attempt < 30; attempt++) {
            Response response = restClient.get(
                    baseUrl,
                    endpoint,
                    Collections.<String, String>emptyMap(),
                    Collections.singletonMap("waveRunId", waveRunId),
                    AuthType.BEARER_TOKEN,
                    ContentType.JSON);

            if (response.getStatusCode() == 200) {
                status = response.jsonPath().getInt("status");

                if (status == 6090) {
                    return status;
                }
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Wave run polling interrupted", e);
            }
        }

        throw new AssertionError(
                "Wave run did not reach 6090. Last status: " + status);
    }
}
