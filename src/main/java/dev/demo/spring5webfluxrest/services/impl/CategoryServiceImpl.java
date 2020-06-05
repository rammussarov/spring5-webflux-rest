package dev.demo.spring5webfluxrest.services.impl;

import dev.demo.spring5webfluxrest.commands.CategoryCommand;
import dev.demo.spring5webfluxrest.coverters.CategoryCommandToCategoryConverter;
import dev.demo.spring5webfluxrest.coverters.CategoryToCategoryCommandConverter;
import dev.demo.spring5webfluxrest.domain.Category;
import dev.demo.spring5webfluxrest.repositories.CategoryRepository;
import dev.demo.spring5webfluxrest.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryToCategoryCommandConverter converter;
    private final CategoryCommandToCategoryConverter backConverter;

    @Override
    public Flux<CategoryCommand> findAll() {
        return categoryRepository.findAll().map(converter::convert);
    }

    @Override
    public Mono<CategoryCommand> findById(String id) {
        return categoryRepository.findById(id).map(converter::convert);
    }

    @Override
    public Mono<Void> save(CategoryCommand command) {
        final Category convert = backConverter.convert(command);
        return categoryRepository.save(convert).then();
    }

    @Override
    public Mono<CategoryCommand> update(String id, CategoryCommand categoryCommand) {
        categoryCommand.setId(id);
        final Category convert = backConverter.convert(categoryCommand);
        final Mono<Category> savedCategory = categoryRepository.save(convert);
        return savedCategory.map(converter::convert);
    }

}
