package dev.demo.spring5webfluxrest.controllers;

import dev.demo.spring5webfluxrest.commands.VendorCommand;
import dev.demo.spring5webfluxrest.services.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/api/v1/vendors")
    @ResponseStatus(HttpStatus.CREATED)
    Mono<Void> create(@RequestBody VendorCommand vendorCommand) {
        return vendorService.save(vendorCommand);
    }

    @PutMapping("/api/v1/vendors/{id}")
    Mono<VendorCommand> update(@PathVariable String id, @RequestBody VendorCommand vendorCommand) {
        return vendorService.update(id, vendorCommand);
    }
}
