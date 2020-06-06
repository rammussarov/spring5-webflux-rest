package dev.demo.spring5webfluxrest.controllers;

import dev.demo.spring5webfluxrest.commands.CategoryCommand;
import dev.demo.spring5webfluxrest.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/api/v1/categories")
    @ResponseStatus(HttpStatus.CREATED)
    Mono<Void> save(@RequestBody CategoryCommand categoryCommand) {
        return categoryService.save(categoryCommand);
    }

    @PutMapping("/api/v1/categories/{id}")
    Mono<CategoryCommand> update(@PathVariable String id, @RequestBody CategoryCommand categoryCommand) {
        return categoryService.update(id, categoryCommand);
    }

    @PatchMapping("/api/v1/categories/{id}")
    Mono<CategoryCommand> patch(@PathVariable String id, @RequestBody CategoryCommand categoryCommand) {
        return categoryService.update(id, categoryCommand);
    }
}
