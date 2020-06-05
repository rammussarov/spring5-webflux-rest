package dev.demo.spring5webfluxrest.services.impl;

import dev.demo.spring5webfluxrest.commands.VendorCommand;
import dev.demo.spring5webfluxrest.coverters.VendorToVendorCommandConverter;
import dev.demo.spring5webfluxrest.domain.Vendor;
import dev.demo.spring5webfluxrest.repositories.VendorRepository;
import dev.demo.spring5webfluxrest.services.VendorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class VendorServiceImplTest {

    @Mock
    private VendorRepository vendorRepository;

    private VendorToVendorCommandConverter converter;

    private VendorService vendorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        converter = new VendorToVendorCommandConverter();
        vendorService = new VendorServiceImpl(vendorRepository, converter);
    }

    @Test
    void findAll() {
        when(vendorRepository.findAll())
                .thenReturn(Flux.just(
                        Vendor.builder().firstName("vendor1").build(),
                        Vendor.builder().firstName("vendor2").build(),
                        Vendor.builder().firstName("vendor3").build())
                );

        final Flux<VendorCommand> all = vendorService.findAll();
        assertEquals(3, all.count().block());
    }

    @Test
    void findById() {
        final String testId = "id1";

        when(vendorRepository.findById(anyString())).thenReturn(Mono.just(Vendor.builder().id(testId).firstName("vendor1").build()));

        final Mono<VendorCommand> vendorMono = vendorService.findById(testId);
        assertEquals(testId, vendorMono.block().getId());
    }
}