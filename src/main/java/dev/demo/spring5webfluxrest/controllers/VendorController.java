package dev.demo.spring5webfluxrest.controllers;

import dev.demo.spring5webfluxrest.commands.VendorCommand;
import dev.demo.spring5webfluxrest.services.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class VendorController {

    private final VendorService vendorService;

    @GetMapping("/api/v1/vendors")
    Flux<VendorCommand> getCategories() {
        return vendorService.findAll();
    }

    @GetMapping("/api/v1/vendors/{id}")
    Mono<VendorCommand> findById(@PathVariable String id) {
        return vendorService.findById(id);
    }
}
