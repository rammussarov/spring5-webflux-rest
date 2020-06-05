package dev.demo.spring5webfluxrest.services;

import dev.demo.spring5webfluxrest.commands.CategoryCommand;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoryService {
    Flux<CategoryCommand> findAll();

    Mono<CategoryCommand> findById(String id);
}
