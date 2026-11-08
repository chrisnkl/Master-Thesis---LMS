package com.chrisnkl.aiquizfunc;

import com.chrisnkl.aiquizfunc.dto.request.DocumentProcessRequest;
import com.chrisnkl.aiquizfunc.exception.ProcessFailedException;
import com.chrisnkl.aiquizfunc.service.DocumentProcessService;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import tools.jackson.databind.ObjectMapper;

import java.util.Optional;

public class DocumentProcessFunction {

    private DocumentProcessService documentProcessService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @FunctionName("DocumentProcessFunction")
    public HttpResponseMessage processDocument(
            @HttpTrigger(
                    name = "process",
                    route = "/api/v1/process"
            ) HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {

        try {

            DocumentProcessRequest processRequest = DocumentProcessRequest.create(request, objectMapper);

            documentProcessService.process(processRequest);

        } catch (ProcessFailedException e) {

        } catch (Exception e) {

        }
        return null;
    }


}
