package com.distributed.customer.util;

public final class LogHeaders {

    private LogHeaders() {
    }

    public static final String CORRELATION_ID = "X-Correlation-Id";
    public static final String PARENT_SPAN_ID = "X-Parent-Span-Id";
    public static final String MDC_CORRELATION_ID = "correlationId";
    public static final String MDC_PARENT_SPAN_ID = "parentSpanId";
}
