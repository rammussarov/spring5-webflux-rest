package dev.demo.spring5webfluxrest.coverters;

import dev.demo.spring5webfluxrest.commands.VendorCommand;
import dev.demo.spring5webfluxrest.domain.Vendor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VendorCommandToVendorConverterTest {

    private static final String ID_VALUE = "testId";
    private static final String FIRST_NAME = "First name";
    private static final String LAST_NAME = "Last name";

    private VendorCommandToVendorConverter converter;

    @BeforeEach
    void setUp() {
        converter = new VendorCommandToVendorConverter();
    }

    @Test
    void testNull() {
        assertNull(converter.convert(null));
    }

    @Test
    void testNotNull() {
        assertNotNull(converter.convert(new VendorCommand()));
    }

    @Test
    void convert() {
        final VendorCommand vendorCommand = VendorCommand.builder().id(ID_VALUE).firstName(FIRST_NAME).lastName(LAST_NAME).build();

        final Vendor convert = converter.convert(vendorCommand);

        assertEquals(ID_VALUE, convert.getId());
        assertEquals(FIRST_NAME, convert.getFirstName());
        assertEquals(LAST_NAME, convert.getLastName());
    }
}