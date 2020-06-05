package dev.demo.spring5webfluxrest.coverters;

import dev.demo.spring5webfluxrest.commands.CategoryCommand;
import dev.demo.spring5webfluxrest.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryCommandToCategoryConverterTest {

    private static final String ID_VALUE = "testId";
    private static final String DESCRIPTION = "test";

    private CategoryCommandToCategoryConverter converter;

    @BeforeEach
    void setUp() {
        converter = new CategoryCommandToCategoryConverter();
    }

    @Test
    void testNull() {
        assertNull(converter.convert(null));
    }

    @Test
    void testNotNull() {
        assertNotNull(converter.convert(new CategoryCommand()));
    }

    @Test
    void convert() {
        CategoryCommand categoryCommand = CategoryCommand.builder().id(ID_VALUE).description(DESCRIPTION).build();

        final Category convert = converter.convert(categoryCommand);

        assertEquals(ID_VALUE, convert.getId());
        assertEquals(DESCRIPTION, convert.getDescription());
    }
}