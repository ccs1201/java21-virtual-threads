package br.com.ccs.java21virtualthreads.controller;

import br.com.ccs.java21virtualthreads.service.FiboService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/test")
public class PthreadController {

    private final AtomicLong countPlatform = new AtomicLong(0);
    private final AtomicLong somaTempoPlatform = new AtomicLong(0);
    private final AtomicLong countCustom = new AtomicLong(0);
    private final AtomicLong somaTempoCustom = new AtomicLong(0);
    private final Executor controllersExecutor;
    private final FiboService service;
    private final Logger log = Logger.getLogger(this.getClass().getSimpleName());

    public PthreadController(@Qualifier("threadPool") Executor controllersExecutor, FiboService service) {
        this.controllersExecutor = controllersExecutor;
        this.service = service;
    }

    @GetMapping("/forkjoin")
    @ResponseStatus(HttpStatus.OK)
    public CompletableFuture<BigInteger> forkjoin() {
        long count = countPlatform.incrementAndGet();

        return CompletableFuture.supplyAsync(() -> {
            long start = System.currentTimeMillis();
            var resultado = service.calcularFibo();

            var tempoTotal = System.currentTimeMillis() - start;

            log.info("Requisição forkjoin Nº" + count + " Tempo Total: " + tempoTotal + "/ms");
            log.info("");

            somaTempoPlatform.addAndGet(tempoTotal);

            return resultado;
        }, ForkJoinPool.commonPool());
    }

    @GetMapping("/custom")
    @ResponseStatus(HttpStatus.OK)
    public CompletableFuture<BigInteger> custom() {
        long count = countCustom.incrementAndGet();

        return CompletableFuture.supplyAsync(() -> {
            long start = System.currentTimeMillis();
            var resultado = service.calcularFibo();

            var tempoTotal = System.currentTimeMillis() - start;

            log.info("Requisição Custom Nº" + count + " Tempo Total: " + tempoTotal + "/ms");
            log.info("");

            somaTempoCustom.addAndGet(tempoTotal);

            return resultado;
        }, controllersExecutor);
    }

    @GetMapping("/total")
    @ResponseStatus(HttpStatus.OK)
    public void total() {
        if (somaTempoPlatform.get() > 0) {
            log.info("Total Requisições forkjoin: " + countPlatform.get() + " Tempo Total: " + somaTempoPlatform.get() / 1000 + "/s Média: " + somaTempoPlatform.get() / countPlatform.get() + "/ms");
        }
        if (somaTempoCustom.get() > 0) {
            log.info("Total Requisições Custom: " + countCustom.get() + " Tempo Total: " + somaTempoCustom.get() / 1000 + "/s Média: " + somaTempoCustom.get() / countCustom.get() + "/ms");
        }
        countPlatform.set(0);
        somaTempoPlatform.set(0);
        countCustom.set(0);
        somaTempoCustom.set(0);
    }
}
