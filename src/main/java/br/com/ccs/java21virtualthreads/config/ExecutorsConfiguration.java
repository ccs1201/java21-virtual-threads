package br.com.ccs.java21virtualthreads.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ExecutorsConfiguration {

    @Bean("threadPool")
    public Executor controllersExecutor() {

        return new ThreadPoolExecutor(16, 16, 60,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(500, true));
    }
}
