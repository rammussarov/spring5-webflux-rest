package dev.demo.spring5webfluxrest.controllers;

import dev.demo.spring5webfluxrest.commands.CategoryCommand;
import dev.demo.spring5webfluxrest.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/api/v1/categories")
    Flux<CategoryCommand> getCategories() {
        return categoryService.findAll();
    }

    @GetMapping("/api/v1/categories/{id}")
    Mono<CategoryCommand> findById(@PathVariable String id) {
        return categoryService.findById(id);
    }
}
