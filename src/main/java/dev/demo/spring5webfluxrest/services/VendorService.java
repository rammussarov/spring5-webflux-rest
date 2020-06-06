package dev.demo.spring5webfluxrest.services;

import dev.demo.spring5webfluxrest.commands.VendorCommand;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface VendorService {
    Flux<VendorCommand> findAll();

    Mono<VendorCommand> findById(String id);

    Mono<Void> save(VendorCommand vendorCommand);

    Mono<VendorCommand> update(String id, VendorCommand vendorCommand);
}
