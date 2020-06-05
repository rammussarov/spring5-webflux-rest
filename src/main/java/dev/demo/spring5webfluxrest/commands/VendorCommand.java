package dev.demo.spring5webfluxrest.commands;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendorCommand {
    private String id;
    private String firstName;
    private String lastName;
}
