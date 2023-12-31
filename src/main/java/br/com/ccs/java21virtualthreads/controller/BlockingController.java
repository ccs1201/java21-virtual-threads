package br.com.ccs.java21virtualthreads.controller;

import br.com.ccs.java21virtualthreads.service.FiboService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;

@RestController
@RequestMapping("/api/blocking")
public class BlockingController {

    private static final int REPETICOES = 10_000;
    private final FiboService service;
    private final Logger log = Logger.getLogger(this.getClass().getSimpleName());
    private final AtomicLong countVirutal = new AtomicLong(0);
    private final AtomicLong sumVirtual = new AtomicLong(0);
    private final AtomicLong countForkjoin = new AtomicLong(0);
    private final AtomicLong sumForkJoin = new AtomicLong(0);

    public BlockingController(FiboService service) {
        this.service = service;
    }

    @RequestMapping("/virtual")
    @ResponseStatus(HttpStatus.OK)
    public CompletableFuture<Void> virtual() {
        long count = countVirutal.incrementAndGet();
        long start = System.currentTimeMillis();

        return CompletableFuture.runAsync(() -> {
            String startThread = Thread.currentThread().toString();

            service.calcularFibo(REPETICOES);

//            log.info("Virtual Nº" + count + " Start: " + LocalDateTime.now());
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            var total = System.currentTimeMillis() - start;

            log.info("Virtual Nº" + count + " Start at Thread -> [" + startThread + "] End at Thread -> [" + Thread.currentThread() + "] | Total time: " + total);

            sumVirtual.addAndGet(total);
        }, Executors.newVirtualThreadPerTaskExecutor());
    }

    @RequestMapping("/forkjoin")
    @ResponseStatus(HttpStatus.OK)
    public CompletableFuture<Void> forkjoin() {
        long count = countForkjoin.incrementAndGet();
        long start = System.currentTimeMillis();

        return CompletableFuture.runAsync(() -> {
            String startThread = Thread.currentThread().toString();

//            log.info("ForkJoin Nº" + count + " Start: " + LocalDateTime.now());

            service.calcularFibo(REPETICOES);
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            var total = System.currentTimeMillis() - start;

            log.info("ForkJoin Nº" + count + " Start at Thread -> [" + startThread + "] End at Thread -> [" + Thread.currentThread() + "] | Total time: " + total);

            sumForkJoin.addAndGet(total);
        }, ForkJoinPool.commonPool());
    }

    @GetMapping("/total")
    @ResponseStatus(HttpStatus.OK)
    public void total() {
        if (countForkjoin.get() > 0) {
            log.info("Total Requisições forkjoin: " + countForkjoin.get() + " Tempo Total: " + sumForkJoin.get() + "/ms Média: " + sumForkJoin.get() / countForkjoin.get() + "/ms");
        }
        if (countVirutal.get() > 0) {
            log.info("Total Requisições Virtual: " + countVirutal.get() + " Tempo Total: " + sumVirtual.get() + "m/s Média: " + sumVirtual.get() / countVirutal.get() + "/ms");
        }
        countVirutal.set(0);
        sumVirtual.set(0);
        countForkjoin.set(0);
        sumForkJoin.set(0);
    }
}
