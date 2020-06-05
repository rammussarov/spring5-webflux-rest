package dev.demo.spring5webfluxrest.coverters;

import dev.demo.spring5webfluxrest.commands.CategoryCommand;
import dev.demo.spring5webfluxrest.domain.Category;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryCommandToCategoryConverter implements Converter<CategoryCommand, Category> {

    @Override
    public Category convert(CategoryCommand categoryCommand) {
        if (categoryCommand == null) return null;

        return Category.builder()
                .id(categoryCommand.getId())
                .description(categoryCommand.getDescription())
                .build();
    }
}
