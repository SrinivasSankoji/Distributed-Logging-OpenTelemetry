package com.distributed.customer.filter;

import com.distributed.customer.util.LogHeaders;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public class RequestTimingFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(RequestTimingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        long startNanos = System.nanoTime();
        String correlationId = Optional.ofNullable(request.getHeader(LogHeaders.CORRELATION_ID))
                .filter(value -> !value.isBlank()).orElse("CORR-" + UUID.randomUUID());
        String parentSpanId = Optional.ofNullable(request.getHeader(LogHeaders.PARENT_SPAN_ID)).orElse("");
        MDC.put(LogHeaders.MDC_CORRELATION_ID, correlationId);
        MDC.put(LogHeaders.MDC_PARENT_SPAN_ID, parentSpanId);
        response.setHeader(LogHeaders.CORRELATION_ID, correlationId);
        if (!parentSpanId.isBlank()) {
            response.setHeader(LogHeaders.PARENT_SPAN_ID, parentSpanId);
        }
        try {
            filterChain.doFilter(request, response);
        } finally {
            long durationMs = (System.nanoTime() - startNanos) / 1_000_000;
            log.info("API_REQUEST_COMPLETED method={} path={} status={} durationMs={}",
                    request.getMethod(),
                    request.getRequestURI(),
                    response.getStatus(),
                    durationMs);
            MDC.remove(LogHeaders.MDC_CORRELATION_ID);
            MDC.remove(LogHeaders.MDC_PARENT_SPAN_ID);
        }
    }
}
