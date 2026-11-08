package com.chrisnkl.aiquizfunc.dto.request;

import com.chrisnkl.aiquizfunc.domain.DifficultyLevel;
import com.chrisnkl.aiquizfunc.exception.BusinessValidationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.functions.HttpRequestMessage;

import java.util.List;
import java.util.Optional;

public record DocumentProcessRequest(

    List<String> filesPath,
    DifficultyLevel difficulty,
    String comment

) {

    public DocumentProcessRequest {
        if (filesPath == null || filesPath.isEmpty()) throw new BusinessValidationException(String.format("Request value '%s' must not be empty or null.", filesPath));
    }

    public static DocumentProcessRequest create(HttpRequestMessage<Optional<String>> request, ObjectMapper objectMapper) {

        String requestBody = request.getBody().orElseThrow(() -> new BusinessValidationException("Missing request body"));
        if (requestBody.isBlank()) throw new BusinessValidationException("Request body must not be empty or blank");

        try {
            return objectMapper.readValue(requestBody, DocumentProcessRequest.class);
        } catch (JsonProcessingException e) {
            throw new BusinessValidationException("Failed to parse request body: " + e.getMessage());
        }
    }
}
