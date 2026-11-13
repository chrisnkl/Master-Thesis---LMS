package com.chrisnkl.langfunc;

import com.chrisnkl.langfunc.app.AppContainer;
import com.chrisnkl.langfunc.domain.SupportedLanguage;
import com.chrisnkl.langfunc.exception.LanguageMissingException;
import com.chrisnkl.langfunc.exception.UnsupportedLanguageException;
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

import java.util.Map;
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
            ) HttpRequestMessage<Optional<String>> request
    ) {


        return executeWithTracing(request, () -> {
            String language = request.getQueryParameters().getOrDefault("language", "");

            try {
                log.info("LanguageFunction has been triggered. Processing request...");
                if (language == null || language.isBlank()) throw new DomainException("Missing or blank 'language' query parameter");

                log.info("Processing request language: {}", language);
                Map<String, String> translations = languageHandler.getTranslations(SupportedLanguage.findByLanguageCode(language));

                log.info("Successfully retrieved translations for language: {}", language);
                return ApiResponse.ok("Translations have been successfully retrieved.", translations).toHttpResponse(request);

            } catch (DomainException e) {
                log.error("Domain error occurred while processing language request for language: {}", language, e);
                return ApiResponse.badRequest(e.getMessage()).toHttpResponse(request);
            } catch (LanguageMissingException e) {
                log.error("Language missing for code: {}", language, e);
                return ApiResponse.notFound(e.getMessage()).toHttpResponse(request);
            } catch (UnsupportedLanguageException e) {
                log.error("Unsupported language code: {}", language, e);
                return ApiResponse.badRequest(e.getMessage()).toHttpResponse(request);
            } catch (Exception e) {
                log.error("Error occurred while processing language request for language: {}", language, e);
                return ApiResponse.internalError(e.getMessage()).toHttpResponse(request);
            }
        });
    }

}
