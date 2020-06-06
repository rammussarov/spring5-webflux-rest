package dev.demo.spring5webfluxrest.services.impl;

import dev.demo.spring5webfluxrest.commands.VendorCommand;
import dev.demo.spring5webfluxrest.coverters.VendorCommandToVendorConverter;
import dev.demo.spring5webfluxrest.coverters.VendorToVendorCommandConverter;
import dev.demo.spring5webfluxrest.domain.Vendor;
import dev.demo.spring5webfluxrest.repositories.VendorRepository;
import dev.demo.spring5webfluxrest.services.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;
    private final VendorToVendorCommandConverter converter;
    private final VendorCommandToVendorConverter backConverter;

    @Override
    public Flux<VendorCommand> findAll() {
        return vendorRepository.findAll().map(converter::convert);
    }

    @Override
    public Mono<VendorCommand> findById(String id) {
        return vendorRepository.findById(id).map(converter::convert);
    }

    @Override
    public Mono<Void> save(VendorCommand vendorCommand) {
        final Vendor convert = backConverter.convert(vendorCommand);
        return vendorRepository.save(convert).then();
    }

    @Override
    public Mono<VendorCommand> update(String id, VendorCommand vendorCommand) {
        vendorCommand.setId(id);
        final Vendor convert = backConverter.convert(vendorCommand);
        final Mono<Vendor> savedValue = vendorRepository.save(convert);
        return savedValue.map(converter::convert);
    }
}
