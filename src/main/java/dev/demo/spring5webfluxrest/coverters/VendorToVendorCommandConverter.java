package dev.demo.spring5webfluxrest.coverters;

import dev.demo.spring5webfluxrest.commands.VendorCommand;
import dev.demo.spring5webfluxrest.domain.Vendor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class VendorToVendorCommandConverter implements Converter<Vendor, VendorCommand> {

    @Override
    @Synchronized
    @Nullable
    public VendorCommand convert(Vendor vendor) {
        if (vendor == null) return null;

        return VendorCommand.builder()
                .id(vendor.getId())
                .firstName(vendor.getFirstName())
                .lastName(vendor.getLastName())
                .build();
    }
}
