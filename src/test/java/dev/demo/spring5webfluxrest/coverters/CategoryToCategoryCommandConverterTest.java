package dev.demo.spring5webfluxrest.coverters;

import dev.demo.spring5webfluxrest.commands.CategoryCommand;
import dev.demo.spring5webfluxrest.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryToCategoryCommandConverterTest {

    private static final String ID_VALUE = "id1";
    private static final String DESCRIPTION = "test description";

    private CategoryToCategoryCommandConverter converter;

    @BeforeEach
    void setUp() {
        converter = new CategoryToCategoryCommandConverter();
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new Category()));
    }

    @Test
    void convert() {
        Category category = Category.builder().id(ID_VALUE).description(DESCRIPTION).build();

        final CategoryCommand categoryCommand = converter.convert(category);

        assertEquals(ID_VALUE, categoryCommand.getId());
        assertEquals(DESCRIPTION, categoryCommand.getDescription());
    }
}