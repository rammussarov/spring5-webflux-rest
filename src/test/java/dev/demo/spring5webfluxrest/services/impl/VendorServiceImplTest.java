package dev.demo.spring5webfluxrest.services.impl;

import dev.demo.spring5webfluxrest.commands.VendorCommand;
import dev.demo.spring5webfluxrest.coverters.VendorCommandToVendorConverter;
import dev.demo.spring5webfluxrest.coverters.VendorToVendorCommandConverter;
import dev.demo.spring5webfluxrest.domain.Vendor;
import dev.demo.spring5webfluxrest.repositories.VendorRepository;
import dev.demo.spring5webfluxrest.services.VendorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class VendorServiceImplTest {

    @Mock
    private VendorRepository vendorRepository;

    private VendorToVendorCommandConverter converter;
    private VendorCommandToVendorConverter backConverter;

    private VendorService vendorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        converter = new VendorToVendorCommandConverter();
        backConverter = new VendorCommandToVendorConverter();
        vendorService = new VendorServiceImpl(vendorRepository, converter, backConverter);
    }

    @Test
    void findAllTest() {
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
    void findByIdTest() {
        final String testId = "id1";

        when(vendorRepository.findById(anyString())).thenReturn(Mono.just(Vendor.builder().id(testId).firstName("vendor1").build()));

        final Mono<VendorCommand> vendorMono = vendorService.findById(testId);
        assertEquals(testId, vendorMono.block().getId());
    }

    @Test
    void saveTest() {
        final String firstName = "test1";
        final String lastName = "test2";
        final String id = "testId";

        final VendorCommand vendorCommand = VendorCommand.builder().firstName(firstName).lastName(lastName).build();
        final Vendor vendor = Vendor.builder().id(id).firstName(firstName).lastName(lastName).build();

        given(vendorRepository.save(any(Vendor.class))).willReturn(Mono.just(vendor));

        ArgumentCaptor<Vendor> argumentCaptor = ArgumentCaptor.forClass(Vendor.class);

        vendorService.save(vendorCommand);

        verify(vendorRepository, times(1)).save(argumentCaptor.capture());
        final Vendor value = argumentCaptor.getValue();
        assertEquals(vendorCommand.getFirstName(), value.getFirstName());
    }

    @Test
    void updateTest() {
        final String id = "testId";
        final String firstName = "test1";
        final String lastName = "test2";

        final VendorCommand vendorCommand = VendorCommand.builder().firstName(firstName).lastName(lastName).build();
        final Vendor vendor = Vendor.builder().id(id).firstName(firstName).lastName(lastName).build();

        given(vendorRepository.save(any(Vendor.class))).willReturn(Mono.just(vendor));

        final Mono<VendorCommand> updatedValue = vendorService.update(id, vendorCommand);

        verify(vendorRepository, times(1)).save(any(Vendor.class));
        assertEquals(id, updatedValue.block().getId());
    }

    @Test
    void patchTest() {
        final String id = "testId";
        final String firstName = "test1";
        final String lastName = "test2";
        final String newFirstName = "First";
        final String newLastName = "Last";

        final VendorCommand vendorCommand = VendorCommand.builder().firstName(firstName).lastName(lastName).build();
        final Vendor vendor = Vendor.builder().id(id).firstName(firstName).lastName(lastName).build();
        final Vendor updatedVendor = Vendor.builder().firstName(newFirstName).lastName(newLastName).build();

        given(vendorRepository.findById(anyString())).willReturn(Mono.just(vendor));
        given(vendorRepository.save(any(Vendor.class))).willReturn(Mono.just(updatedVendor));

        final Mono<VendorCommand> updatedValue = vendorService.patch(id, vendorCommand);

        verify(vendorRepository, times(1)).save(any(Vendor.class));
        assertEquals(updatedVendor.getFirstName(), updatedValue.block().getFirstName());
        assertEquals(updatedVendor.getLastName(), updatedValue.block().getLastName());
    }

    @Test
    void patchSaveNotCalledTest() {
        final String id = "testId";
        final String firstName = "test1";
        final String lastName = "test2";

        final VendorCommand vendorCommand = VendorCommand.builder().firstName(firstName).lastName(lastName).build();

        given(vendorRepository.findById(anyString())).willReturn(Mono.empty());

        vendorService.patch(id, vendorCommand);

        verify(vendorRepository, never()).save(any(Vendor.class));
    }
}