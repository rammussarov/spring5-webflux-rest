package dev.demo.spring5webfluxrest.services.impl;

import dev.demo.spring5webfluxrest.commands.CategoryCommand;
import dev.demo.spring5webfluxrest.coverters.CategoryToCategoryCommandConverter;
import dev.demo.spring5webfluxrest.domain.Category;
import dev.demo.spring5webfluxrest.repositories.CategoryRepository;
import dev.demo.spring5webfluxrest.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    private CategoryToCategoryCommandConverter converter;

    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        converter = new CategoryToCategoryCommandConverter();
        categoryService = new CategoryServiceImpl(categoryRepository, converter);
    }

    @Test
    void findAll() {
        when(categoryRepository.findAll()).thenReturn(Flux.just(
                Category.builder().description("cat1").build(),
                Category.builder().description("cat2").build()
        ));

        Flux<CategoryCommand> all = categoryService.findAll();
        assertEquals(2, all.count().block());
    }

    @Test
    void findById() {
        final String testId = "id1";
        when(categoryRepository.findById(anyString())).thenReturn(Mono.just(
                Category.builder().id(testId).description("cat1").build()
        ));

        Mono<CategoryCommand> categoryMono = categoryService.findById(testId);
        assertEquals(testId, categoryMono.block().getId());
    }
}