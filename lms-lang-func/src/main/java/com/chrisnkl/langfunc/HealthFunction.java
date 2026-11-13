package com.chrisnkl.langfunc;

import com.chrisnkl.libs.domain.ApiResponse;
import com.chrisnkl.libs.helper.AbstractFunction;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class HealthFunction extends AbstractFunction {

    // Function to act as health probe for the service. It responds with a simple message indicating that the service is up (200 - OK) and running.
    @FunctionName("HealthFunction")
    public HttpResponseMessage run(
            @HttpTrigger(
                    name = "req",
                    methods = {HttpMethod.GET},
                    authLevel = AuthorizationLevel.ANONYMOUS,
                    route = "health"
            ) HttpRequestMessage<Optional<String>> request
    ) {

        return executeWithTracing(request, () -> {
            log.info("HealthFunction has been triggered. Processing request...");
            final String message = "Health check successful. The service is up and running.";
            return ApiResponse.ok(message).toHttpResponse(request);
        });
    }
}
