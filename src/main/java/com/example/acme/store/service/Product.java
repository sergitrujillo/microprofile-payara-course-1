package com.example.acme.store.service;

import javax.json.bind.annotation.JsonbVisibility;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@JsonbVisibility(FieldPropertyVisibilityStrategy.class)
public class Product {
    @NotNull(message = "Name mandatory")
    @Size(max = 100, message = "name should be at max 100 chars")
    private String name;

    @NotNull(message = "Description mandatory")
    @Size(min = 5, message = "description should be at least 5 chars")
    private String description;

    @NotNull(message = "Quantity mandatory")
    @Min(value = 0, message = "Quantity should be higher than 0")
    private Long quantity;

    public String getName() {
        return name;
    }
}
