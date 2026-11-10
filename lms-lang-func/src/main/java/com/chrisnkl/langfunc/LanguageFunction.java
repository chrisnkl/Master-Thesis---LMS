package com.chrisnkl.langfunc;

import com.chrisnkl.langfunc.app.AppContainer;
import com.chrisnkl.langfunc.handler.LanguageHandler;
import com.chrisnkl.libs.exception.impl.DomainException;
import com.chrisnkl.libs.helper.AbstractFunction;
import com.chrisnkl.libs.domain.ApiResponse;
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
public class LanguageFunction extends AbstractFunction {

    private final LanguageHandler languageHandler = AppContainer.getLanguageHandler();

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


        executeWithTracing(request, () -> {
            try {
                log.info("LanguageFunction invoked");

                String lang = request.getQueryParameters().get("lang");
                if (lang == null || lang.isBlank()) throw new DomainException("Missing or blank 'lang' query parameter");
                log.info("Processing request language: {}", lang);

                languageHandler.handleLanguage(lang);
                return ApiResponse.ok("Language function operational", null).toHttpResponse(request);
            } catch (Exception e) {
                return ApiResponse.internalError(e.getMessage()).toHttpResponse(request);
            }
        });



    }

}
