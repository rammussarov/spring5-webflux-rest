package dev.demo.spring5webfluxrest.services.impl;

import dev.demo.spring5webfluxrest.commands.CategoryCommand;
import dev.demo.spring5webfluxrest.coverters.CategoryCommandToCategoryConverter;
import dev.demo.spring5webfluxrest.coverters.CategoryToCategoryCommandConverter;
import dev.demo.spring5webfluxrest.domain.Category;
import dev.demo.spring5webfluxrest.repositories.CategoryRepository;
import dev.demo.spring5webfluxrest.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    private CategoryToCategoryCommandConverter converter;
    private CategoryCommandToCategoryConverter backConverter;

    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        converter = new CategoryToCategoryCommandConverter();
        backConverter = new CategoryCommandToCategoryConverter();
        categoryService = new CategoryServiceImpl(categoryRepository, converter, backConverter);
    }

    @Test
    void findAllTest() {
        when(categoryRepository.findAll()).thenReturn(Flux.just(
                Category.builder().description("cat1").build(),
                Category.builder().description("cat2").build()
        ));

        Flux<CategoryCommand> all = categoryService.findAll();
        assertEquals(2, all.count().block());
    }

    @Test
    void findByIdTest() {
        final String testId = "id1";
        when(categoryRepository.findById(anyString())).thenReturn(Mono.just(
                Category.builder().id(testId).description("cat1").build()
        ));

        Mono<CategoryCommand> categoryMono = categoryService.findById(testId);
        assertEquals(testId, categoryMono.block().getId());
    }

    @Test
    void saveTest() {
        final CategoryCommand categoryCommand = CategoryCommand.builder()
                .description("test")
                .build();

        final Category category = Category.builder()
                .id("testId")
                .description("test")
                .build();

        given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(category));

        ArgumentCaptor<Category> argumentCaptor = ArgumentCaptor.forClass(Category.class);

        categoryService.save(categoryCommand);

        verify(categoryRepository, times(1)).save(argumentCaptor.capture());
        final Category captorValue = argumentCaptor.getValue();
        assertEquals(categoryCommand.getDescription(), captorValue.getDescription());
    }

    @Test
    void updateTest() {
        final String id = "testId";
        final String description = "test123";

        final CategoryCommand categoryCommand = CategoryCommand.builder()
                .description("test")
                .build();


        final Category category = Category.builder()
                .id(id)
                .description(description)
                .build();

        given(categoryRepository.save(any(Category.class))).willReturn(Mono.just(category));

        final Mono<CategoryCommand> updatedValue = categoryService.update(id, categoryCommand);

        verify(categoryRepository, times(1)).save(any(Category.class));
        assertEquals(description, updatedValue.block().getDescription());
    }

    @Test
    void patchTest() {
        final String id = "testId";
        final String description = "test";
        final String newDescription = "test123";

        final CategoryCommand categoryCommand = CategoryCommand.builder().id(id).description(description).build();
        final Category category = Category.builder().id(id).description(description).build();
        final Category updatedCategory = Category.builder().id(id).description(newDescription).build();

        given(categoryRepository.findById(anyString())).willReturn(Mono.just(category));
        given(categoryRepository.save(any(Category.class))).willReturn(Mono.just(updatedCategory));

        Mono<CategoryCommand> saved = categoryService.patch(id, categoryCommand);

        verify(categoryRepository, times(1)).save(any(Category.class));
        assertEquals(updatedCategory.getDescription(), saved.block().getDescription());
    }

    @Test
    void patchSaveNotCalledTest() {
        final String id = "testId";
        final String description = "test";

        final CategoryCommand categoryCommand = CategoryCommand.builder().id(id).description(description).build();

        given(categoryRepository.findById(anyString())).willReturn(Mono.empty());

        categoryService.patch(id, categoryCommand);

        verify(categoryRepository, never()).save(any(Category.class));
    }
}