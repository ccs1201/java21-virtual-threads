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
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/test")
public class VthreadController {
    private final Logger log = Logger.getLogger(this.getClass().getSimpleName());
    private final AtomicLong countVirutal = new AtomicLong(0);
    private final AtomicLong somaTempoVirtual = new AtomicLong(0);
    private final FiboService service;

    public VthreadController(FiboService service) {
        this.service = service;
    }

    @GetMapping("/virtual")
    @ResponseStatus(HttpStatus.OK)
    public CompletableFuture<BigInteger> virtual() {
        long count = countVirutal.incrementAndGet();

        return CompletableFuture.supplyAsync(() -> {
            long start = System.currentTimeMillis();
            var resultado = service.calcularFibo();

            var tempoTotal = System.currentTimeMillis() - start;

            log.info("Requisição Virtual Nº" + count + " Tempo Total: " + tempoTotal + "/ms");
            log.info("");

            somaTempoVirtual.addAndGet(tempoTotal);

            return resultado;
        }, Executors.newVirtualThreadPerTaskExecutor());
    }

    @GetMapping("/totalvirtual")
    @ResponseStatus(HttpStatus.OK)
    public void total() {
        if (countVirutal.get() > 0) {
            log.info("Total Requisições Virtual: " + countVirutal.get() + " Tempo Total: " + somaTempoVirtual.get() / 1000 + "/s Média: " + somaTempoVirtual.get() / countVirutal.get() + "/ms");
        }

        countVirutal.set(0);
        somaTempoVirtual.set(0);
    }
}
