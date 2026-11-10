package com.chrisnkl.libs.helper;

import com.chrisnkl.libs.enums.LoggingConstants;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import io.opentelemetry.api.trace.Span;
import org.slf4j.MDC;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

public abstract class AbstractFunction {

    protected HttpResponseMessage executeWithTracing(
            HttpRequestMessage<Optional<String>> request,
            Supplier<HttpResponseMessage> action
    ) {
        String traceId = resolveTraceId(request);
        MDC.put(LoggingConstants.TRACE_ID.getValue(), traceId);
        try {
            return action.get();
        } finally {
            MDC.remove(LoggingConstants.TRACE_ID.getValue());
        }
    }

    private String resolveTraceId(HttpRequestMessage<Optional<String>> request) {
        String traceId = Span.current().getSpanContext().getTraceId();
        if (traceId != null && !traceId.equals("00000000000000000000000000000000")) return traceId;
        // 00-{traceId}-{spanId}-{flags}
        String traceparent = request.getHeaders().get("traceparent");
        if (traceparent != null) {
            String[] parts = traceparent.split("-");
            if (parts.length >= 3) {
                return parts[1];
            }
        }
        return UUID.randomUUID().toString().replace("-", "");
    }

    public HttpResponseMessage createBackendResponse(HttpRequestMessage<Optional<String>> request, int status) {
        return null;
    }

}

