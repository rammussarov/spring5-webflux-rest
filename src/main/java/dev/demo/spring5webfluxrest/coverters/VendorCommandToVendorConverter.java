package dev.demo.spring5webfluxrest.coverters;

import dev.demo.spring5webfluxrest.commands.VendorCommand;
import dev.demo.spring5webfluxrest.domain.Vendor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class VendorCommandToVendorConverter implements Converter<VendorCommand, Vendor> {

    @Override
    @Synchronized
    @Nullable
    public Vendor convert(VendorCommand vendorCommand) {
        if (vendorCommand == null) return null;

        return Vendor.builder()
                .id(vendorCommand.getId())
                .firstName(vendorCommand.getFirstName())
                .lastName(vendorCommand.getLastName())
                .build();
    }
}
