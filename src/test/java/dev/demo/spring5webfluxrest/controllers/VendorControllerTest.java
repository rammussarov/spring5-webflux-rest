package dev.demo.spring5webfluxrest.controllers;

import dev.demo.spring5webfluxrest.commands.VendorCommand;
import dev.demo.spring5webfluxrest.services.VendorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

class VendorControllerTest {

    private WebTestClient webTestClient;

    @Mock
    private VendorService vendorService;

    @InjectMocks
    private VendorController vendorController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        webTestClient = WebTestClient.bindToController(vendorController).build();
    }

    @Test
    void getCategories() {
        when(vendorService.findAll())
                .thenReturn(Flux.just(
                        VendorCommand.builder().id("id1").firstName("name1").build(),
                        VendorCommand.builder().id("id2").firstName("name2").build(),
                        VendorCommand.builder().id("id3").firstName("name3").build()
                ));

        webTestClient.get()
                .uri("/api/v1/vendors")
                .exchange()
                .expectBodyList(VendorCommand.class)
                .hasSize(3);
    }

    @Test
    void findById() {
        given(vendorService.findById(anyString()))
                .willReturn(Mono.just(
                        VendorCommand.builder().id("id1").lastName("last name").build()
                ));

        webTestClient.get()
                .uri("/api/v1/vendors/id1")
                .exchange()
                .expectBody(VendorCommand.class);
    }

    @Test
    void save() {
        VendorCommand vendorCommand = VendorCommand.builder().firstName("first").lastName("last").build();

        given(vendorService.save(any(VendorCommand.class))).willReturn(Mono.empty());

        webTestClient
                .post()
                .uri("/api/v1/vendors")
                .body(Mono.just(vendorCommand), VendorCommand.class)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    void update() {
        final String id = "id";
        final String firstName = "first";
        final String lastName = "last";

        final VendorCommand vendorCommand = VendorCommand.builder().firstName(firstName).lastName(lastName).build();
        final VendorCommand savedCommand = VendorCommand.builder().id(id).firstName(firstName).lastName(lastName).build();

        given(vendorService.update(id, vendorCommand)).willReturn(Mono.just(savedCommand));

        webTestClient
                .put()
                .uri("/api/v1/vendors/id1")
                .body(Mono.just(vendorCommand), VendorCommand.class)
                .exchange()
                .expectStatus().isOk();
    }
}