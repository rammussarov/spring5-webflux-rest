package dev.demo.spring5webfluxrest.coverters;

import dev.demo.spring5webfluxrest.commands.VendorCommand;
import dev.demo.spring5webfluxrest.domain.Vendor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VendorToVendorCommandConverterTest {

    private static final String ID_VALUE = "idTest";
    private static final String FIRST_NAME = "Vendor 1";
    private static final String LAST_NAME = "Vendor N";

    private VendorToVendorCommandConverter converter;

    @BeforeEach
    void setUp() {
        converter = new VendorToVendorCommandConverter();
    }

    @Test
    void testNull() {
        assertNull(converter.convert(null));
    }

    @Test
    void testNotNull() {
        assertNotNull(new Vendor());
    }

    @Test
    void convert() {
        Vendor vendor = Vendor.builder().id(ID_VALUE).firstName(FIRST_NAME).lastName(LAST_NAME).build();

        final VendorCommand convert = converter.convert(vendor);

        assertEquals(ID_VALUE, convert.getId());
        assertEquals(FIRST_NAME, convert.getFirstName());
        assertEquals(LAST_NAME, convert.getLastName());
    }
}