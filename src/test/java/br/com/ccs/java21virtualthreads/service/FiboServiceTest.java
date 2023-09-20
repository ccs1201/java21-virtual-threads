package br.com.ccs.java21virtualthreads.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FiboServiceTest {

    @InjectMocks
    private FiboService service;

    @Test
    void calcularFibo() {

        service.calcularFibo();

    }
}
