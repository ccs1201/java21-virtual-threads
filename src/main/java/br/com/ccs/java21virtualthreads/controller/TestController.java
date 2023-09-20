package br.com.ccs.java21virtualthreads.controller;

import br.com.ccs.java21virtualthreads.service.FiboService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/test")
public class TestController {

    private static AtomicLong countPlatform = new AtomicLong(0);
    private static AtomicLong countVirutal = new AtomicLong(0);
    private final FiboService service;
    private final Logger log = Logger.getLogger(this.getClass().getSimpleName());

    public TestController(FiboService service) {
        this.service = service;
    }

    @GetMapping("/platform")
    @ResponseStatus(HttpStatus.OK)
    public CompletableFuture<BigInteger> platform() {
        long start = System.currentTimeMillis();
        long count = countPlatform.incrementAndGet();

        return CompletableFuture.supplyAsync(() -> {
            var resultado = service.calcularFibo();

            log.info("Requisição Platform Nº" + count + " Tempo Total: " + (System.currentTimeMillis() - start) + "/ms");
            log.info("");

            return resultado;
        }, ForkJoinPool.commonPool());
    }

    @GetMapping("/virtual")
    @ResponseStatus(HttpStatus.OK)
    public CompletableFuture<BigInteger> virtual() {
        long start = System.currentTimeMillis();
        long count = countVirutal.incrementAndGet();

        return CompletableFuture.supplyAsync(() -> {
            var resultado = service.calcularFibo();

            log.info("Requisição Virtual Nº" + count + " Tempo Total: " + (System.currentTimeMillis() - start) + "/ms");
            log.info("");

            return resultado;
        }, Executors.newVirtualThreadPerTaskExecutor());
    }
}
