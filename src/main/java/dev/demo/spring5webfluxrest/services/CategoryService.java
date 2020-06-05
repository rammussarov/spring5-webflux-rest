package dev.demo.spring5webfluxrest.services;

import dev.demo.spring5webfluxrest.domain.Category;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoryService {
    Flux<Category> findAll();

    Mono<Category> findById(String id);
}
