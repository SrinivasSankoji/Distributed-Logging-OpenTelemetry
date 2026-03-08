package com.distributed.customer;

import com.distributed.customer.filter.RequestTimingFilter;
import com.distributed.customer.util.LogHeaders;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import org.slf4j.MDC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder, Tracer tracer) {
        ClientHttpRequestInterceptor interceptor = (request, body, execution) -> {
            String correlationId = MDC.get(LogHeaders.MDC_CORRELATION_ID);
            if (correlationId != null && !correlationId.isBlank()) {
                request.getHeaders().set(LogHeaders.CORRELATION_ID, correlationId);
            }
            Span currentSpan = tracer.currentSpan();
            if (currentSpan != null) {
                request.getHeaders().set(LogHeaders.PARENT_SPAN_ID, currentSpan.context().spanId());
            }
            return execution.execute(request, body);
        };
        return builder.additionalInterceptors(interceptor).build();
    }

    @Bean
    FilterRegistrationBean<RequestTimingFilter> requestTimingFilter() {
        FilterRegistrationBean<RequestTimingFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new RequestTimingFilter());
        bean.addUrlPatterns("/*");
        bean.setOrder(1);
        return bean;
    }
}
