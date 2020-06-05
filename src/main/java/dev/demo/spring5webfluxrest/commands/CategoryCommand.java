package dev.demo.spring5webfluxrest.commands;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryCommand {
    private String id;
    private String description;
}
