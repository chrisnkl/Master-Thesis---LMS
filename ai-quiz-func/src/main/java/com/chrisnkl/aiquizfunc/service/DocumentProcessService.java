package com.chrisnkl.aiquizfunc.service;

import com.chrisnkl.aiquizfunc.dto.request.DocumentProcessRequest;
import com.chrisnkl.aiquizfunc.dto.response.DocumentProcessResponse;
import com.chrisnkl.aiquizfunc.exception.BusinessValidationException;
import com.chrisnkl.aiquizfunc.exception.ProcessFailedException;

public interface DocumentProcessService {

    DocumentProcessResponse process(DocumentProcessRequest request) throws BusinessValidationException, ProcessFailedException;

}
