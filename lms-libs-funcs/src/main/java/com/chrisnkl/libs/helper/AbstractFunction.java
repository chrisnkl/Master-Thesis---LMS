package com.chrisnkl.libs.helper;

import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;

import java.util.Optional;

public abstract class AbstractFunction {

    public HttpResponseMessage createBackendResponse(HttpRequestMessage<Optional<String>> request, int status) {
        return null;
    }

}

