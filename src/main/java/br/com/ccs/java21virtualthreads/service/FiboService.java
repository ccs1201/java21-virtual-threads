package br.com.ccs.java21virtualthreads.service;

import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class FiboService {

    private static final int REPETICOES = 100_000;

    public BigInteger calcularFibo() {

        BigInteger resultado = BigInteger.valueOf(1);
        BigInteger antecessor = BigInteger.valueOf(0);

        for (int i = 1; i < REPETICOES; i++) {

            resultado = resultado.add(antecessor);

            antecessor = resultado.subtract(antecessor);
        }
        return resultado;
    }
}
