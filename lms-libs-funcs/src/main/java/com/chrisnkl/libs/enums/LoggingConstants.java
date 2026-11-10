package com.chrisnkl.libs.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum LoggingConstants {

    TRACE_ID("traceId");

    private final String value;

}
