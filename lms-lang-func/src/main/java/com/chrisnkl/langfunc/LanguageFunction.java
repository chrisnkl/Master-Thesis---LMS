package com.chrisnkl.langfunc;

import com.chrisnkl.langfunc.factory.ServiceFactory;
import com.chrisnkl.langfunc.handler.ILanguageHandler;
import com.chrisnkl.libs.helper.AbstractFunction;
import com.chrisnkl.libs.domain.ApiResponse;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import java.util.Optional;

public class LanguageFunction extends AbstractFunction {

    private final ILanguageHandler iLanguageHandler;

    public LanguageFunction(ILanguageHandler iLanguageHandler) {
        this.iLanguageHandler = iLanguageHandler;
    }

    public LanguageFunction() {
        this.iLanguageHandler = ServiceFactory.getLanguageHandler();
    }

    @FunctionName("LanguageFunction")
    public HttpResponseMessage run(
            @HttpTrigger(
                    name = "req",
                    methods = {HttpMethod.GET},
                    authLevel = AuthorizationLevel.ANONYMOUS,
                    route = "lang"
            ) HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context
    ) {
        try {
            return ApiResponse.ok("Language function operational", null).toHttpResponse(request);
        } catch (Exception e) {
            return ApiResponse.internalError(e.getMessage()).toHttpResponse(request);
        }
    }

}
