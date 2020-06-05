package dev.demo.spring5webfluxrest.controllers;

import dev.demo.spring5webfluxrest.commands.CategoryCommand;
import dev.demo.spring5webfluxrest.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

class CategoryControllerTest {

    private WebTestClient webTestClient;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        webTestClient = WebTestClient.bindToController(categoryController).build();
    }

    @Test
    void getCategories() {
        given(categoryService.findAll()).willReturn(Flux.just(
                CategoryCommand.builder().description("cat1").build(),
                CategoryCommand.builder().description("cat2").build()
        ));

        webTestClient
                .get().uri("/api/v1/categories")
                .exchange()
                .expectBodyList(CategoryCommand.class)
                .hasSize(2);
    }

    @Test
    void findById() {
        given(categoryService.findById(anyString()))
                .willReturn(Mono.just(CategoryCommand.builder().description("cat1").build()));

        webTestClient.get().uri("/api/v1/categories/abc")
                .exchange()
                .expectBody(CategoryCommand.class);
    }

    @Test
    void save() {
        CategoryCommand categoryCommand = CategoryCommand.builder().description("test desc").build();
        given(categoryService.save(any(CategoryCommand.class)))
                .willReturn(Mono.empty());

        webTestClient
                .post()
                .uri("/api/v1/categories")
                .body(Mono.just(categoryCommand), CategoryCommand.class)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    void update() {
        final CategoryCommand categoryCommand = CategoryCommand.builder().description("test desc").build();
        final String id = "testId";
        final CategoryCommand savedCategory = CategoryCommand.builder().id(id).description("new desc").build();

        given(categoryService.update(id, categoryCommand)).willReturn(Mono.just(savedCategory));

        webTestClient
                .put()
                .uri("/api/v1/categories/testId")
                .body(Mono.just(categoryCommand), CategoryCommand.class)
                .exchange()
                .expectStatus().isOk();
    }
}