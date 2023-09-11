package meetona.data.config;

import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;
import org.springframework.core.task.TaskExecutor;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Map;


@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    public AsyncConfig() {
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }

    @Override
    @Bean(name = "taskExecutor")
    public TaskExecutor getAsyncExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(200);
        executor.setThreadNamePrefix("userThread-");
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setTaskDecorator(new ContextCopyDecorator());
        executor.initialize();
        return new DelegatingSecurityContextAsyncTaskExecutor(executor);
    }

    @Bean
    public DelegatingSecurityContextAsyncTaskExecutor taskExecutor
            (ThreadPoolTaskExecutor delegate) {
        return new DelegatingSecurityContextAsyncTaskExecutor(delegate);
    }


    private static class ContextCopyDecorator implements TaskDecorator {
        @NonNull
        @Override
        public Runnable decorate(@NonNull Runnable runnable) {
            // store context in variables which will be bound to the executor thread
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            SecurityContext securityContext = SecurityContextHolder.getContext();
            Map<String, String> mdcContextMap = MDC.getCopyOfContextMap();
            return () -> { // code runs inside executor thread and binds context
                try {
                    if (requestAttributes != null) {
                        RequestContextHolder.setRequestAttributes(requestAttributes);
                    }
                    if (securityContext != null) {
                        SecurityContextHolder.setContext(securityContext);
                    }
                    if (mdcContextMap != null) {
                        MDC.setContextMap(mdcContextMap);
                    }
                    runnable.run();
                } finally {
                    MDC.clear();
                    RequestContextHolder.resetRequestAttributes();
                    SecurityContextHolder.clearContext();
                }
            };
        }
    }

}
